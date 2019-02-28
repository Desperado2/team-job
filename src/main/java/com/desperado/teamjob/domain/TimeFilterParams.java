
package com.desperado.teamjob.domain;

public class TimeFilterParams {
	/**
	 * 提交日期开始 单位秒
	 */
	protected int submitDateFrom;
	/**
	 * 提交日期结束 单位秒
	 */
	protected int submitDateTo;

	public TimeFilterParams() {
	}

	public TimeFilterParams(int submitDateFrom, int submitDateTo) {
		this.submitDateFrom = submitDateFrom;
		this.submitDateTo = submitDateTo;
	}

	public int getSubmitDateFrom() {
		return submitDateFrom;
	}

	public void setSubmitDateFrom(int submitDateFrom) {
		this.submitDateFrom = submitDateFrom;
	}

	public int getSubmitDateTo() {
		return submitDateTo;
	}

	public void setSubmitDateTo(int submitDateTo) {
		this.submitDateTo = submitDateTo;
	}
}
