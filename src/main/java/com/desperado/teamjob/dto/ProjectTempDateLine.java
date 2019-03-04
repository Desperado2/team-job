package com.desperado.teamjob.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class ProjectTempDateLine {
    @ApiModelProperty("内容")
    String content;
    @ApiModelProperty("时间")
    String timestamp;
    @ApiModelProperty("颜色")
    String color = "#EF9B6C";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
