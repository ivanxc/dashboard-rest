package com.ivanxc.hse.dashboardrest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FormEditDto {
    String title;

    @JsonCreator
    public FormEditDto(@JsonProperty("title") String title) {
        this.title = title;
    }
}
