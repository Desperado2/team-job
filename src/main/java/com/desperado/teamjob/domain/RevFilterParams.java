package com.desperado.teamjob.domain;

public final class RevFilterParams extends TimeFilterParams {
	private String author;
	private String emailAddress;
	private String[] nickNames;
	private String[] commonEmails;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String[] getNickNames() {
		return nickNames;
	}

	public void setNickNames(String[] nickNames) {
		this.nickNames = nickNames;
	}

	public String[] getCommonEmails() {
		return commonEmails;
	}

	public void setCommonEmails(String[] commonEmails) {
		this.commonEmails = commonEmails;
	}
}