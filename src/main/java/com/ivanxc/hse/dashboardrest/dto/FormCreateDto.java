package com.ivanxc.hse.dashboardrest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivanxc.hse.dashboardrest.entity.FormVariant;
import lombok.Value;

@Value
public class FormCreateDto {
    String title;
    FormVariant type;

    @JsonCreator
    public FormCreateDto(@JsonProperty("title") String title, @JsonProperty("type") FormVariant type) {
        this.title = title;
        this.type = type;
    }
}
