package com.desperado.teamjob.domain.res;



import com.desperado.teamjob.dto.UserDto;

import java.io.Serializable;
import java.util.List;

public class WeeklyStatisticModel implements Serializable {

	private static final long serialVersionUID = -9194986860412603316L;
	private String weekNo;
	private List<UserDto> unSubmits;
	private int totalNum;
	private int submitNum;
	
	public String getWeekNo() {
		return weekNo;
	}
	public void setWeekNo(String weekNo) {
		this.weekNo = weekNo;
	}
	public List<UserDto> getUnSubmits() {
		return unSubmits;
	}
	public void setUnSubmits(List<UserDto> unSubmits) {
		this.unSubmits = unSubmits;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getSubmitNum() {
		return submitNum;
	}
	public void setSubmitNum(int submitNum) {
		this.submitNum = submitNum;
	}
	
	

}
