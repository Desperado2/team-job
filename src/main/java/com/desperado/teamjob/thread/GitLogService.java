
package com.desperado.teamjob.thread;

import com.alibaba.fastjson.JSON;
import com.desperado.teamjob.dao.ProjectDao;
import com.desperado.teamjob.dao.UserDao;
import com.desperado.teamjob.domain.*;
import com.desperado.teamjob.domain.res.GitCommitsSummaryModel;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.enums.CommitType;
import com.desperado.teamjob.helper.RepositoryHelper;
import com.desperado.teamjob.utils.DateUtil;
import com.desperado.teamjob.utils.StringUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
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
	private ProjectDao projectDao;
	@Autowired
	private UserDao userDao;

	/**
	 * 获取作者代码行数
	 * </br>可以根据时间筛选，如果时间传0，则不会加上时间条件去筛选
	 * @param gitUrl
	 * @param projectCode
	 * @param submitDateFrom 单位秒
	 * @param submitDateTo 单位秒
	 * @return
	 */
	public void listLinesByAuthor(String gitUrl, String projectCode, Integer submitDateFrom, Integer submitDateTo) {
		List<RevFilterParams> revParamList = buildFilterParams(projectCode, submitDateFrom, submitDateTo);
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl)) {
			try (Git git = new Git(repository)) {
				for (RevFilterParams revFilterParams : revParamList) {
					/*CommitCalc calc = filterLines(repository, git, new UserRevFilter(revFilterParams), projectCode);
					GitWeeklyCommits gitWeeklyCommits = new GitWeeklyCommits();
					gitWeeklyCommits.setAuthor(revFilterParams.getAuthor());
					gitWeeklyCommits.setProject(projectCode);
					gitWeeklyCommits.setTotalAddLines(calc.getTotalAddLines());
					gitWeeklyCommits.setTotalCommits(calc.getTotalCommits());
					gitWeeklyCommits.setTotalDelLines(calc.getTotalDelLines());
					gitWeeklyCommits.setYearweek(DateUtil.getYearWeek(new Date()));*/
					//int count = gitWeeklyCommitsDao.deleteByWeek(DateUtil.getYearWeek(new Date()));
					logger.info("total delete:{}", 0);
					//gitWeeklyCommitsDao.save(gitWeeklyCommits);
				}
			}
		} catch (Exception e) {
			logger.info("{}", e);
		}
	}

	public Repository getRepositoryInfo(String gitUrl){
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl)) {
			return repository;
		}catch (Exception e){
			logger.info("{}", e);
		}
		return null;
	}
	public List<GitCommitLogs> listAllLinesByTime(String gitUrl, String projectCode, Integer submitDateFrom, Integer submitDateTo) {
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl)) {
			try (Git git = new Git(repository)) {
				TimeFilterParams timeFilterParams = new TimeFilterParams(submitDateFrom, submitDateTo);
				List<GitCommitLogs> gitCommitLogs = filterLines(repository, git, new TimeFilter(timeFilterParams), projectCode);
				return gitCommitLogs;
			}
		} catch (Exception e) {
			logger.info("{}", e);
		}
		return null;
	}
	
	public void listAllLines(String gitUrl, String projectCode) {
		try (Repository repository = RepositoryHelper.openJGitRepository(gitUrl)) {
			try (Git git = new Git(repository)) {
				filterLines(repository, git, null, projectCode);
			}
		} catch (Exception e) {
			logger.info("{}", e);
		}
	}

	private List<GitCommitLogs> filterLines(Repository repository, Git git, RevFilter filter, String projectCode)
			throws AmbiguousObjectException, IncorrectObjectTypeException, IOException, GitAPIException, CorruptObjectException, MissingObjectException {
		Collection<Ref> allRefs = repository.getAllRefs().values();
		List<GitCommitLogs> gitCommitLogsList = new ArrayList<>();
		// a RevWalk allows to walk over commits based on some filtering that is defined
		RevWalk walk = new RevWalk(repository);
		for (Ref ref : allRefs) {
			walk.markStart(walk.parseCommit(ref.getObjectId()));
		}
		walk.sort(RevSort.COMMIT_TIME_DESC); // chronological order
		if(filter != null) {//if null walk all
			walk.setRevFilter(filter);
		}
		int delSize = 0;
		int addSize = 0;
		int commits = 0;
		String author = null;
		String email = null;
		for (RevCommit commit : walk) {
			commits++;
			email = commit.getAuthorIdent().getEmailAddress();
			author = commit.getAuthorIdent().getName();
			GitCommitLogs gitCommitLogs = new GitCommitLogs();
			gitCommitLogs.setId(UUID.randomUUID().toString());
			gitCommitLogs.setProject(projectCode);
			//gitCommitLogs.setAuthor(getRealAuthor(author));
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
		/*CommitCalc commitCalc = new CommitCalc();
		commitCalc.setAuthor(author);
		commitCalc.setAuthorEmail(email);
		commitCalc.setTotalAddLines(addSize);
		commitCalc.setTotalDelLines(delSize);
		commitCalc.setTotalCommits(commits);*/
		return gitCommitLogsList;
	}


	private List<RevFilterParams> buildFilterParams(String projectCode, Integer submitDateFrom, Integer submitDateTo) {
		Project project =projectDao.selectProjectById(projectCode);
		String charges = project.getCoder();
		if (StringUtil.isEmpty(charges)) {
			return null;
		}
		String[] charger = charges.split(",");
		List<UserDto> accounts = userDao.selectUserByIds(Arrays.asList(charger));
		RevFilterParams params = null;
		List<RevFilterParams> filterParamsList = new ArrayList<>();
		for (UserDto account : accounts) {
			params = new RevFilterParams();
			params.setAuthor(account.getId());
			params.setEmailAddress(account.getEmail());
			params.setNickNames(account.getName() == null ? null : account.getName().split(","));
			params.setCommonEmails(account.getEmail() == null ? null : account.getEmail().split(","));
			params.setSubmitDateFrom(submitDateFrom == null ? 0 : submitDateFrom);
			params.setSubmitDateTo(submitDateTo == null ? 0 : submitDateTo);
			filterParamsList.add(params);
		}
		return filterParamsList;
	}

	public List<GitCommitLogs> getWeeklyLogs(String author) {
		return null;
		//return gitCommitLogsDao.getWeeklyLogs(DateUtil.getYearWeek(new Date()), author);
	}
	
	/**
	 * 查询提交的数量
	 * @param project
	 * 可以为空，空的时候是查询这个人所有提交
	 * @param author
	 * @param yearweek
	 * 可以为空，空的时候是查询这个人所有提交
	 */
	public GitCommitsSummaryModel getGitCommitSummary(String project, String author, String yearweek) {
		Map<String, Object> map = new HashMap<>();
		map.put("project", project);
		map.put("author", author);
		map.put("yearweek", yearweek);
		map.put("totalAddLines", null);
		map.put("totalDelLines", null);
		map.put("totalCommits", null);
		//gitCommitLogsDao.getCommitSummary(map);
		GitCommitsSummaryModel gcsm = new GitCommitsSummaryModel();
		gcsm.setAuthor(author);
		gcsm.setTotalAddLines((Integer) map.get("totalAddLines"));
		gcsm.setTotalDelLines((Integer) map.get("totalDelLines"));
		gcsm.setTotalCommits((Integer) map.get("totalCommits"));
		return gcsm;
	}

}
