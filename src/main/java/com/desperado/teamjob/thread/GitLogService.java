
package com.desperado.teamjob.thread;

import com.alibaba.fastjson.JSON;
import com.desperado.teamjob.config.GitContentsConfig;
import com.desperado.teamjob.config.SvnContensConfig;
import com.desperado.teamjob.dao.UserDao;
import com.desperado.teamjob.domain.*;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.enums.CommitType;
import com.desperado.teamjob.helper.RepositoryHelper;
import com.desperado.teamjob.utils.DateUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;


@Service("gitLogService")
public class GitLogService {
	private static Logger logger = LoggerFactory.getLogger(GitLogService.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private GitContentsConfig gitContentsConfig;

	public List<GitCommitLogs> listAllLinesByTime(String gitUrl, String projectCode,String projectName, Integer submitDateFrom, Integer submitDateTo) {
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl,gitContentsConfig.getBasePath())) {
			try (Git git = new Git(repository)) {
				TimeFilterParams timeFilterParams = new TimeFilterParams(submitDateFrom, submitDateTo);
				List<GitCommitLogs> gitCommitLogs = filterLines(repository, git, new TimeFilter(timeFilterParams), projectCode,projectName);
				return gitCommitLogs;
			}
		} catch (Exception e) {
			logger.info("{}", e);
		}
		return null;
	}


	private List<GitCommitLogs> filterLines(Repository repository, Git git, RevFilter filter, String projectCode,String projectName)
			throws  IOException, GitAPIException{
		Collection<Ref> allRefs = repository.getAllRefs().values();
		List<GitCommitLogs> gitCommitLogsList = new ArrayList<>();
		RevWalk walk = new RevWalk(repository);
		for (Ref ref : allRefs) {
			walk.markStart(walk.parseCommit(ref.getObjectId()));
		}
		walk.sort(RevSort.COMMIT_TIME_DESC);
		if(filter != null) {
			walk.setRevFilter(filter);
		}
		int delSize = 0;
		int addSize = 0;
		String author = null;
		String email = null;
		for (RevCommit commit : walk) {
			email = commit.getAuthorIdent().getEmailAddress();
			author = commit.getAuthorIdent().getName();
			GitCommitLogs gitCommitLogs = new GitCommitLogs();
			gitCommitLogs.setId(UUID.randomUUID().toString());
			gitCommitLogs.setProject(projectCode);
			gitCommitLogs.setProjectName(projectName);
			gitCommitLogs.setAuthor(getRealAuthor(author,email));
			gitCommitLogs.setCommitId(commit.getId().name());
			gitCommitLogs.setYearweek(DateUtil.getYearWeek(new Date(commit.getCommitTime()*1000L)));
			gitCommitLogs.setCommitComment(commit.getFullMessage());
			gitCommitLogs.setDateCommit(new Date(commit.getCommitTime()*1000L));
			if(commit.getParentCount()>1) {
				gitCommitLogs.setCommitType(CommitType.MERGE.getCode());
			}else {
				gitCommitLogs.setCommitType(CommitType.COMMIT.getCode());
			}
			TreeWalk treeWalk = new TreeWalk(repository);
			ObjectId objectId = commit.getId();
			ObjectReader reader = repository.newObjectReader();
			ObjectId newTreeId = repository.resolve(objectId.name() + "^{tree}");
			ObjectId oldTreeId = repository.resolve(objectId.name() + "^^{tree}");
			CanonicalTreeParser newTree = new CanonicalTreeParser();
			newTree.reset(reader, newTreeId);
			CanonicalTreeParser oldTree = new CanonicalTreeParser();
			if (oldTreeId != null) {
				oldTree.reset(reader, oldTreeId);
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DiffFormatter df = new DiffFormatter(out);
			df.setRepository(repository);
			int totalAddLinesPerCommit = 0;
			int totalDelLinesPerCommit = 0;
			for (DiffEntry de : git.diff().setNewTree(newTree).setOldTree(oldTree).call()) {
				FileHeader fh = df.toFileHeader(de);
				List<? extends HunkHeader> hunks = fh.getHunks();
				for (HunkHeader hunkHeader : hunks) {
					EditList el = hunkHeader.toEditList();
					for (Edit edit : el) {
						totalDelLinesPerCommit += edit.getEndA() - edit.getBeginA();
						delSize += edit.getEndA() - edit.getBeginA();
						totalAddLinesPerCommit += edit.getEndB() - edit.getBeginB();
						addSize += edit.getEndB() - edit.getBeginB();
					}
				}
				df.close();
			}
			gitCommitLogs.setTotalAddLines(totalAddLinesPerCommit);
			gitCommitLogs.setTotalDelLines(totalDelLinesPerCommit);
			try {
				gitCommitLogsList.add(gitCommitLogs);
			} catch (DuplicateKeyException e) {
				logger.error("commit already recorded:{},{}", e, JSON.toJSONString(gitCommitLogs));
				return null;
			} catch(Exception e) {
				logger.error("e:{}, logs:{}", e, JSON.toJSONString(gitCommitLogs));
			} finally {
				treeWalk.close();
			}
		}
		walk.close();
		return gitCommitLogsList;
	}

	private String getRealAuthor(String author,String email){
		UserDto userDto = userDao.selectUserByRepositoryUsername(author);
		if(userDto != null){
			return userDto.getName();
		}
		 userDto = userDao.selectUserByEmail(email);
		if(userDto != null){
			return userDto.getName();
		}
		return "其他人";
	}

}
