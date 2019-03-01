
package com.desperado.teamjob.thread;

import com.alibaba.fastjson.JSON;
import com.desperado.teamjob.config.GitContentsConfig;
import com.desperado.teamjob.config.SvnContensConfig;
import com.desperado.teamjob.dao.UserDao;
import com.desperado.teamjob.domain.GitCommitLogs;
import com.desperado.teamjob.domain.Project;
import com.desperado.teamjob.domain.TimeFilter;
import com.desperado.teamjob.domain.TimeFilterParams;
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
import org.tmatesoft.svn.core.SVNException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;


@Service("svnLogService")
public class SvnLogService {
	private static Logger logger = LoggerFactory.getLogger(SvnLogService.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private SvnContensConfig svnContensConfig;

	public List<GitCommitLogs> listAllLinesByTime(Project project,Date begin, Date end) {
		SvnOperation svnOperation = new SvnOperation();
		try {
			List<GitCommitLogs> logsList = svnOperation.staticticsCodeAddByTime(project,svnContensConfig, begin,end);
			List<GitCommitLogs> commitLogs = dealLines(project, logsList);
			return commitLogs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private List<GitCommitLogs> dealLines(Project project,List<GitCommitLogs> logsList) {
		List<GitCommitLogs> gitCommitLogsList = new ArrayList<>();
		for (GitCommitLogs gitCommitLogs : logsList){
			gitCommitLogs.setCommitId(gitCommitLogs.getCommitId() +"-"+ project.getProjectName());
			gitCommitLogs.setProject(project.getProjectName());
			gitCommitLogs.setProjectName(project.getProjectRealName());
			gitCommitLogs.setAuthor(getRealAuthor(gitCommitLogs.getAuthor()));
			gitCommitLogs.setYearweek(DateUtil.getYearWeek(new Date(gitCommitLogs.getDateCommit().getTime())));
			gitCommitLogsList.add(gitCommitLogs);
		}
		return gitCommitLogsList;
	}

	private String getRealAuthor(String author){
		UserDto userDto = userDao.selectUserByRepositoryUsername(author);
		if(userDto != null){
			return userDto.getName();
		}
		return "其他人";
	}
}
