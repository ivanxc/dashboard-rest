package com.ivanxc.hse.dashboardrest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UserCreateEditDto {
    String name;
    String password;

    @JsonCreator
    public UserCreateEditDto(@JsonProperty("login") String name,
        @JsonProperty("password") String password) {
        this.name = name;
        this.password = password;
    }
}
