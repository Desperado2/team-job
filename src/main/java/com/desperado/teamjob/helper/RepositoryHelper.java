package com.desperado.teamjob.helper;


import com.desperado.teamjob.config.GitContentsConfig;
import com.desperado.teamjob.thread.RepositoryOperation;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class RepositoryHelper {


	private static Logger logger = LoggerFactory.getLogger(RepositoryHelper.class);
	public static Repository openJGitRepository(String gitUrl) throws IOException {
		String path = getGitRepoDir(gitUrl);
		File f = new File(path + "/.git");
		final boolean isNewRepo = !f.exists();
		if(isNewRepo) {
			try {
				RepositoryOperation.cloneWithAuthentication(gitUrl);
			} catch (GitAPIException e) {
				logger.error("openJGitRepository-->{},", e);
			}
		}
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repo =  builder
				.readEnvironment()
				.findGitDir(new File(getGitRepoDir(gitUrl)))
				.build();
		if(!isNewRepo) {
			gitPull(repo);
		}
		return repo;
	}


	
	public static void gitPull(Repository repo) throws RevisionSyntaxException, AmbiguousObjectException, IncorrectObjectTypeException, IOException {
		try (Git git = new Git(repo);) {
			try {
				ObjectId oldHead = repo.resolve("HEAD^{tree}");
				PullResult result = git.pull().call();
				ObjectId head = repo.resolve("HEAD^{tree}");
				ObjectReader reader = repo.newObjectReader();
				CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
				oldTreeIter.reset(reader, oldHead);
				CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
				newTreeIter.reset(reader, head);
				List<DiffEntry> diffs = git.diff()
				                    .setNewTree(newTreeIter)
				                    .setOldTree(oldTreeIter)
				                    .call();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				DiffFormatter df = new DiffFormatter(out);
				df.setRepository(repo);
				for (DiffEntry diff : diffs) {
					diff.getNewPath();
					df.format(diff);
				}
				logger.info(new String(out.toByteArray()));
				df.close();
				MergeStatus status = result.getMergeResult().getMergeStatus();
				logger.info("status ==================" + status.toString());
			} catch (GitAPIException e) {
				logger.info("e:{}", e);
			}
		}
	}

	public static String createGitDir(String sshUrl) {
		File f = new File(getGitRepoDir(sshUrl));
		if (f.exists()) {
			return f.getAbsolutePath();
		}
		if (f.mkdirs()) {
			return f.getAbsolutePath();
		} else {
			return null;
		}
	}

	public static String getRepoDir(String gitUrl) {
		return gitUrl == null ? null : gitUrl.substring(gitUrl.lastIndexOf("/") + 1, gitUrl.lastIndexOf(".git"));
	}

	public static String getGitRepoDir(String gitUrl) {
		String homeDir = "c:/code/testgit/";
		return homeDir + getRepoDir(gitUrl);
	}

	public static String getNewGitRepoDir(String gitUrl) {
		String homeDir = System.getProperty("user.home");
		return homeDir + "/.git_repo_for_new" + "/" + getRepoDir(gitUrl);
	}

	public static Repository createNewRepository(String repoDir) throws IOException {
		// prepare a new folder
		File localPath = new File(getNewGitRepoDir(repoDir));
		// create the directory
		Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
		repository.create();
		return repository;
	}
}