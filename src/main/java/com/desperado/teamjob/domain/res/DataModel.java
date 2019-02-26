package com.desperado.teamjob.domain.res;

import com.desperado.teamjob.dto.UserDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataModel {
	private List<String> thisWeek;
	private UserDto person;
	private List<String> nextWeek;
	private String summary;

	public static DataModel fromWeeklyModel(WeeklyModel weekly) {
		DataModel model = new DataModel();
		//model.setPerson(weekly.getPerson());
		List<String> thisWeeks = new ArrayList<>(Arrays.asList(weekly.getThisWeek().split("\n")));
		thisWeeks.add(0, "本周工作:");
		model.setThisWeek(thisWeeks);
		List<String> nextWeeks = new ArrayList<>(Arrays.asList(weekly.getNextWeek().split("\n")));
		nextWeeks.add(0, "下周计划:");
		model.setNextWeek(nextWeeks);
		model.setSummary(weekly.getSummary());
		return model;
	}

	public List<String> getThisWeek() {
		return thisWeek;
	}

	public void setThisWeek(List<String> thisWeek) {
		this.thisWeek = thisWeek;
	}

	public UserDto getPerson() {
		return person;
	}

	public void setPerson(UserDto person) {
		this.person = person;
	}

	public List<String> getNextWeek() {
		return nextWeek;
	}

	public void setNextWeek(List<String> nextWeek) {
		this.nextWeek = nextWeek;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
