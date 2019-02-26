/**
 * 
 */
package com.desperado.teamjob.enums;

public enum CommitType {
	COMMIT(1),
	MERGE(2);
	private int code;
	private CommitType(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
}
