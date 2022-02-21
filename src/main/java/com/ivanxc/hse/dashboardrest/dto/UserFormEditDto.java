package com.ivanxc.hse.dashboardrest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivanxc.hse.dashboardrest.entity.Role;
import lombok.Value;

@Value
public class UserFormEditDto {
    Role userRole;

    @JsonCreator
    public UserFormEditDto(@JsonProperty("userRole") Role userRole) {
        this.userRole = userRole;
    }
}
