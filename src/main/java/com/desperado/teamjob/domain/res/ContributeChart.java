/**
 * 
 */
package com.desperado.teamjob.domain.res;


import io.swagger.annotations.ApiModelProperty;

/**
 * @author jimmy
 * @since 2018年04月23日
 */
public class ContributeChart {
	@ApiModelProperty("作者")
	private String author;
	@ApiModelProperty("数量")
	private int count;
	public ContributeChart() {
	}
	public ContributeChart(String author, int count) {
		this.author = author;
		this.count = count;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
