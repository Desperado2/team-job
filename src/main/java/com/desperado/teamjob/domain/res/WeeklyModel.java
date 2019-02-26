package com.desperado.teamjob.domain.res;



import com.desperado.teamjob.domain.Weekly;
import com.desperado.teamjob.dto.UserDto;

import java.io.Serializable;

public class WeeklyModel implements Serializable {

	private static final long serialVersionUID = 2941558524422116476L;

	private String id;
	private String weekNo;
	private UserDto person;
	private String thisWeek;
	private String nextWeek;
	private String summary;
	private String createTime;

	public static WeeklyModel fromWeekly(Weekly weekly) {
		WeeklyModel model = new WeeklyModel();
		model.setId(weekly.getId());
	/*	model.setNextWeek(weekly.getNextWeek());
		model.setThisWeek(weekly.getThisWeek());
		model.setSummary(weekly.getSummary());
		model.setWeekNo(weekly.getWeekNo());*/
		return model;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWeekNo() {
		return weekNo;
	}

	public void setWeekNo(String weekNo) {
		this.weekNo = weekNo;
	}

	public UserDto getPerson() {
		return person;
	}

	public void setPerson(UserDto person) {
		this.person = person;
	}

	public String getThisWeek() {
		return thisWeek;
	}

	public void setThisWeek(String thisWeek) {
		this.thisWeek = thisWeek;
	}

	public String getNextWeek() {
		return nextWeek;
	}

	public void setNextWeek(String nextWeek) {
		this.nextWeek = nextWeek;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
