/**
 * 
 */
package com.desperado.teamjob.domain.res;


import io.swagger.annotations.ApiModelProperty;

public class GitCommitsChart {
	@ApiModelProperty("横坐标")
	private String x;
	@ApiModelProperty("纵坐标")
	private Integer y;
	
	public GitCommitsChart() {
	}
	public GitCommitsChart(String x, Integer y) {
		this.x = x;
		this.y = y;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
}
