package com.desperado.teamjob.domain.res;



import com.desperado.teamjob.dto.UserDto;

import java.io.Serializable;
import java.util.List;

public class ProjectModel implements Serializable{
	
	private static final long serialVersionUID = -3340767998224111802L;
	private String code;
	private String name;
	private String gitUrl;
	private String dbAddress;
	private String docUrl;
	
	private List<UserDto> chargers;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGitUrl() {
		return gitUrl;
	}

	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}

	public String getDbAddress() {
		return dbAddress;
	}

	public void setDbAddress(String dbAddress) {
		this.dbAddress = dbAddress;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public List<UserDto> getChargers() {
		return chargers;
	}

	public void setChargers(List<UserDto> chargers) {
		this.chargers = chargers;
	}
	
	
	
	

}
