package com.desperado.teamjob.domain.res;

import java.io.Serializable;
import java.math.BigDecimal;

public class SonarQualityModel implements Serializable {

	private static final long serialVersionUID = 7238400849072688731L;
	private String name;
	private String title;
	private BigDecimal codeLines;
	private BigDecimal bugs;
	private BigDecimal codeSmells;
	private BigDecimal duplicatedLines;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getCodeLines() {
		return codeLines;
	}

	public void setCodeLines(BigDecimal codeLines) {
		this.codeLines = codeLines;
	}

	public BigDecimal getBugs() {
		return bugs;
	}

	public void setBugs(BigDecimal bugs) {
		this.bugs = bugs;
	}

	public BigDecimal getCodeSmells() {
		return codeSmells;
	}

	public void setCodeSmells(BigDecimal codeSmells) {
		this.codeSmells = codeSmells;
	}

	public BigDecimal getDuplicatedLines() {
		return duplicatedLines;
	}

	public void setDuplicatedLines(BigDecimal duplicatedLines) {
		this.duplicatedLines = duplicatedLines;
	}

}
