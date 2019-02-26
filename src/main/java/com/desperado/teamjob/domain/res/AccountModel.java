package com.desperado.teamjob.domain.res;

import java.io.Serializable;

public class AccountModel implements Serializable {

	private static final long serialVersionUID = -4298827509262005211L;
	private String id;
	private String name;
	private String email;
	private String avatar;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
