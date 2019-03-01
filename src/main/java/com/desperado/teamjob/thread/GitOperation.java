package com.desperado.teamjob.thread;


import com.desperado.teamjob.helper.RepositoryHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class GitOperation {

	private static Logger logger = LoggerFactory.getLogger(GitOperation.class);

	public static String commitAll(String gitUrl) throws NoFilepatternException, GitAPIException, IOException {
		// prepare a new test-repository
		try (Repository repository = RepositoryHelper.createNewRepository(gitUrl);) {
			try (Git git = new Git(repository);) {
				// create the file
				File myfile = new File(repository.getDirectory().getParent(), "testfile");
				// Stage all files in the repo including new files
				git.add().addFilepattern(".").call();
				// and then commit the changes.
				git.commit().setMessage("Commit all changes including additions").call();
				try (PrintWriter writer = new PrintWriter(myfile)) {
					writer.append("Hello, world!");
				}
				// Stage all changed files, omitting new files, and commit with
				// one command
				git.commit().setAll(true).setMessage("Commit changes to all files").call();
				logger.info("Committed all changes to repository at " + repository.getDirectory());
			}
		}
		return null;
	}
	
	public static String checkout(String gitUrl, String branchName) throws NoFilepatternException, GitAPIException, IOException {
		// prepare a new test-repository
		try (Repository repository = RepositoryHelper.createNewRepository(gitUrl);) {
			try (Git git = new Git(repository);) {
				git.checkout().setName(branchName).call();
			}
		}
		return null;
	}

	public static List<Ref> listBranch(boolean isListAllBranch, String gitUrl,String targetPath) throws IOException, GitAPIException {
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl,targetPath)) {
			logger.info("Listing local branches:");
			try (Git git = new Git(repository)) {
				if (isListAllBranch) {
					return git.branchList().call();
				} else {
					return git.branchList().setListMode(ListMode.ALL).call();
				}
			}
		}
	}

	public static String fetchRemoteBranchWithPrune(String remoteUrl,String targetPath)
			throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		try (Repository repository = RepositoryHelper.openJGitRepository(remoteUrl,targetPath);) {
			try (Git git = new Git(repository);) {
				logger.info("Starting fetch");
				FetchResult result = git.fetch().setCheckFetchedObjects(true).call();
				return result.getMessages();
			}
		}
	}

	public static String listNotes(String gitUrl,String targetPath) throws GitAPIException, MissingObjectException, IOException {
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl,targetPath)) {
			try (Git git = new Git(repository)) {
				List<Note> call = git.notesList().call();
				logger.info("Listing " + call.size() + " notes");
				for (Note note : call) {
					logger.info("Note: " + note + " " + note.getName() + " " + note.getData().getName()
							+ "\nContent: ");
					// displaying the contents of the note is done via a simple
					// blob-read
					ObjectLoader loader = repository.open(note.getData());
					loader.copyTo(System.out);
				}
			}
		}
		return null;
	}

	public static void listRemoteRepository(String remoteUrl)
			throws InvalidRemoteException, TransportException, GitAPIException {
		// then clone
		logger.info("Listing remote repository " + remoteUrl);
		Collection<Ref> refs =
				Git.lsRemoteRepository().setHeads(true).setTags(true).setRemote(remoteUrl).call();
		for (Ref ref : refs) {
			logger.info("Ref: " + ref);
		}
		final Map<String, Ref> map =
				Git.lsRemoteRepository().setHeads(true).setTags(true).setRemote(remoteUrl).callAsMap();
		logger.info("As map");
		for (Map.Entry<String, Ref> entry : map.entrySet()) {
			logger.info("Key: " + entry.getKey() + ", Ref: " + entry.getValue());
		}
		refs = Git.lsRemoteRepository().setRemote(remoteUrl).call();
		logger.info("All refs");
		for (Ref ref : refs) {
			logger.info("Ref: " + ref);
		}
	}

	public static void showBranchDiff(String gitUrl, String branchA, String branchB,String targetPath) throws IOException, RefAlreadyExistsException, RefNotFoundException,
            InvalidRefNameException, GitAPIException {
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl,targetPath)) {
			try (Git git = new Git(repository)) {
				if (repository.exactRef("refs/heads/" + branchB) == null) {
					// first we need to ensure that the remote branch is visible
					Ref ref = git.branchCreate().setName("master").setStartPoint("refs/heads/" + branchA).call();
					logger.info("Created local testbranch with ref: " + ref);
				}
				AbstractTreeIterator oldTreeParser = prepareTreeParserWithRef(repository, "refs/heads/" + branchB);
				AbstractTreeIterator newTreeParser = prepareTreeParserWithRef(repository, "refs/heads/" + branchA);
				// then the procelain diff-command returns a list of diff
				// entries
				List<DiffEntry> diff = git.diff().setOldTree(oldTreeParser).setNewTree(newTreeParser).call();
				for (DiffEntry entry : diff) {
					try (DiffFormatter formatter = new DiffFormatter(System.out)) {
						formatter.setRepository(repository);
						formatter.format(entry);
					}
				}
			}
		}
	}
	
	public static String showCommitDiff(String gitUrl, String oldCommit, String newCommit,String targetPath) throws IOException, RefAlreadyExistsException, RefNotFoundException,
            InvalidRefNameException, GitAPIException {
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl,targetPath)) {
			try (Git git = new Git(repository)) {
				return listDiff(repository, git, oldCommit, newCommit);
			}
		}
	}

	private static String listDiff(Repository repository, Git git, String oldCommit, String newCommit) {
        try {
			ObjectId oldOjectId = repository.resolve(oldCommit + "^{tree}");
			
			ObjectId newObjectId = null;
			if(newCommit == null) {
				newObjectId = repository.resolve("HEAD^{tree}");
			} else {
				newObjectId = repository.resolve(newCommit + "^{tree}");
			}
			CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
			ObjectReader reader = repository.newObjectReader();
			oldTreeParser.reset(reader, oldOjectId);
			CanonicalTreeParser newTreeParser = new CanonicalTreeParser();
			newTreeParser.reset(reader, newObjectId);
			
			final List<DiffEntry> diffs = git.diff()
					.setOldTree(oldTreeParser)
					.setNewTree(newTreeParser)
					.call();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DiffFormatter df = new DiffFormatter(out);
			df.setRepository(repository);
			for(DiffEntry diff : diffs) {
				df.format(diff);
			}
			final String result = new String(out.toByteArray());
			df.close();
			return result;
		} catch (RevisionSyntaxException | IOException | GitAPIException e) {
			logger.error("GitOperation.listDiff error:{}", e);
			return null;
		}
    }
	
	public static void showLogs(String gitUrl,String targetPath) throws Exception {
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl,targetPath)) {
			try (Git git = new Git(repository)) {
				Iterable<RevCommit> logs = git.log().call();
				int count = 0;
				for (RevCommit rev : logs) {
					logger.info(
							"Commit: " + rev /*
												 * + ", name: " + rev.getName() + ", id: " + rev.getId().getName()
												 */);
					count++;
				}
				logger.info("Had " + count + " commits overall on current branch");
				logs = git.log().add(repository.resolve("remotes/origin/testbranch")).call();
				count = 0;
				for (RevCommit rev : logs) {
					logger.info(
							"Commit: " + rev /*
												 * + ", name: " + rev.getName() + ", id: " + rev.getId().getName()
												 */);
					count++;
				}
				logger.info("Had " + count + " commits overall on test-branch");
				logs = git.log().not(repository.resolve("master"))
						.add(repository.resolve("remotes/origin/testbranch")).call();
				count = 0;
				for (RevCommit rev : logs) {
					logger.info(
							"Commit: " + rev /*
												 * + ", name: " + rev.getName() + ", id: " + rev.getId().getName()
												 */);
					count++;
				}
				logger.info("Had " + count + " commits only on test-branch");
				logs = git.log().all().call();
				count = 0;
				for (RevCommit rev : logs) {
					logger.info(
							"Commit: " + rev /*
												 * + ", name: " + rev.getName() + ", id: " + rev.getId().getName()
												 */);
					count++;
				}
				logger.info("Had " + count + " commits overall in repository");
				logs = git.log()
						// for all log.all()
						.addPath("README.md").call();
				count = 0;
				for (RevCommit rev : logs) {
					logger.info(
							"Commit: " + rev /*
												 * + ", name: " + rev.getName() + ", id: " + rev.getId().getName()
												 */);
					count++;
				}
				logger.info("Had " + count + " commits on README.md");
				logs = git.log()
						// for all log.all()
						.addPath("pom.xml").call();
				count = 0;
				for (RevCommit rev : logs) {
					logger.info(
							"Commit: " + rev /*
												 * + ", name: " + rev.getName() + ", id: " + rev.getId().getName()
												 */);
					count++;
				}
				logger.info("Had " + count + " commits on pom.xml");
			}
		}
	}

	public static void pull(String gitUrl,String targetPath) throws Exception {
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl,targetPath)) {
			try (Git git = new Git(repository)) {
				PullResult pullResult = git.pull().call();
				pullResult.getFetchedFrom();
			}
		}
	}

	public static void showStatus(String gitUrl,String targetPath) throws NoWorkTreeException, GitAPIException, IOException {
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl,targetPath)) {
			try (Git git = new Git(repository)) {
				Status status = git.status().call();
				logger.info("Added: " + status.getAdded());
				logger.info("Changed: " + status.getChanged());
				logger.info("Conflicting: " + status.getConflicting());
				logger.info("ConflictingStageState: " + status.getConflictingStageState());
				logger.info("IgnoredNotInIndex: " + status.getIgnoredNotInIndex());
				logger.info("Missing: " + status.getMissing());
				logger.info("Modified: " + status.getModified());
				logger.info("Removed: " + status.getRemoved());
				logger.info("Untracked: " + status.getUntracked());
				logger.info("UntrackedFolders: " + status.getUntrackedFolders());
			}
		}
	}
	
    private static AbstractTreeIterator prepareTreeParserWithRef(Repository repository, String ref) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        Ref head = repository.exactRef(ref);
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(head.getObjectId());
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        }
    }
	
	public static AbstractTreeIterator prepareTreeParserWithCommit(Repository repository, String objectId) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        //noinspection Duplicates
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(repository.resolve(objectId));
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        }
    }

	public static void getUserConfig(String gitUrl,String targetPath) throws IOException {
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl,targetPath)) {
			Config config = repository.getConfig();
			String name = config.getString("user", null, "name");
			String email = config.getString("user", null, "email");
			if (name == null || email == null) {
				logger.info("User identity is unknown!");
			} else {
				logger.info("User identity is " + name + " <" + email + ">");
			}
			String url = config.getString("remote", "origin", "url");
			if (url != null) {
				logger.info("Origin comes from " + url);
			}
		}
	}
}
