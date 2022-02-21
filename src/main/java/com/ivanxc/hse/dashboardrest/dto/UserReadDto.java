package com.ivanxc.hse.dashboardrest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class UserReadDto {
    long id;
    String name;
    LocalDateTime lastActivity;

    @JsonCreator
    public UserReadDto(@JsonProperty("id") long id, @JsonProperty("name") String name,
        @JsonProperty("lastActivity") LocalDateTime lastActivity) {
        this.id = id;
        this.name = name;
        this.lastActivity = lastActivity;
    }
}
