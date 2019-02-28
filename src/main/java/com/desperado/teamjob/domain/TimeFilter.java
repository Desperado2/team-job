package com.desperado.teamjob.domain;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;

import java.io.IOException;


public class TimeFilter extends RevFilter {
	private TimeFilterParams timeFilterParams;
	public TimeFilter(TimeFilterParams timeFilterParams) {
		this.timeFilterParams = timeFilterParams;
	}
	@Override
	public boolean include(RevWalk walker, RevCommit commit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
		boolean isValidateTime = false;
		int commitTime = commit.getCommitTime();
		if(timeFilterParams.submitDateFrom > 0 && timeFilterParams.submitDateTo > 0) {
			isValidateTime = buildSubmitDate(commitTime, timeFilterParams);
		} else if(timeFilterParams.submitDateFrom > 0 && timeFilterParams.submitDateTo == 0) {
			timeFilterParams.submitDateTo = (int) (System.currentTimeMillis()/1000);
			isValidateTime = buildSubmitDate(commitTime, timeFilterParams);
		}
		return isValidateTime;
	}
	
	/**
	 * @param commitTime 
	 * @param filterParams 
	 * @return
	 */
	private boolean buildSubmitDate(int commitTime, TimeFilterParams filterParams) {
		if(commitTime >= filterParams.submitDateFrom && commitTime <= filterParams.submitDateTo) {
			return true;
		}
		return false;
	}

	@Override
	public RevFilter clone() {
		return this;
	}
}
