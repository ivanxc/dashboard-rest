package com.ivanxc.hse.dashboardrest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivanxc.hse.dashboardrest.entity.FormVariant;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class FormReadDto {
    Long id;
    String title;
    FormVariant type;
    LocalDateTime created;
    LocalDateTime updated;

    @JsonCreator
    public FormReadDto(@JsonProperty("id") Long id, @JsonProperty("title") String title,
        @JsonProperty("type") FormVariant type, @JsonProperty("created") LocalDateTime created,
        @JsonProperty("updated") LocalDateTime updated) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.created = created;
        this.updated = updated;
    }
}
