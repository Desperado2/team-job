package com.desperado.teamjob.domain;


import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class UserRevFilter extends RevFilter {
		private RevFilterParams filterParams;

		public UserRevFilter(RevFilterParams filterParams) {
			this.filterParams = filterParams;
		}

		@Override
		public boolean include(RevWalk walker, RevCommit commit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
			return sameUser(commit, filterParams);
		}

		@Override
		public RevFilter clone() {
			return this;
		}

		private boolean sameUser(RevCommit commit, RevFilterParams filterParams) {
			PersonIdent id = commit.getAuthorIdent();
			boolean isValidateTime = false;
			boolean needTime = false;
			int commitTime = commit.getCommitTime();
			if(filterParams.submitDateFrom > 0 && filterParams.submitDateTo > 0) {
				needTime = true;
				isValidateTime = buildSubmitDate(commitTime, filterParams);
			} else if(filterParams.submitDateFrom > 0 && filterParams.submitDateTo == 0) {
				needTime = true;
				filterParams.submitDateTo = (int) (System.currentTimeMillis()/1000);
				isValidateTime = buildSubmitDate(commitTime, filterParams);
			}
			boolean sameUser = id.getName().equalsIgnoreCase(filterParams.getAuthor()) ||
					arrayEqualsIgnoreCase(id.getName(), filterParams.getNickNames()) ||
					id.getEmailAddress().equalsIgnoreCase(filterParams.getEmailAddress()) ||
					arrayEqualsIgnoreCase(id.getEmailAddress(), filterParams.getCommonEmails());
			if(needTime) {
				return sameUser && isValidateTime;
			}
			return sameUser;
		}

		/**
		 * @param commitTime 
		 * @param filterParams 
		 * @return
		 */
		private boolean buildSubmitDate(int commitTime, RevFilterParams filterParams) {
			if(commitTime >= filterParams.submitDateFrom && commitTime <= filterParams.submitDateTo) {
				return true;
			}
			return false;
		}

		private boolean arrayEqualsIgnoreCase(String a, String[] b) {
			if (StringUtils.isEmpty(a)) {
				return false;
			}
			if (b == null || b.length <= 0) {
				return false;
			}
			for (String s : b) {
				if (a.equalsIgnoreCase(s)) {
					return true;
				}
			}
			return false;
		}
	}