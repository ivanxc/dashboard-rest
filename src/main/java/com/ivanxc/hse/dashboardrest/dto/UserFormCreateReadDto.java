package com.ivanxc.hse.dashboardrest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivanxc.hse.dashboardrest.entity.Role;
import lombok.Value;

@Value
public class UserFormCreateReadDto {
    Long userId;
    Long formId;
    Role userRole;

    @JsonCreator
    public UserFormCreateReadDto(@JsonProperty("userId") Long userId,
        @JsonProperty("formId") Long formId,
        @JsonProperty("userRole") Role userRole) {
        this.userId = userId;
        this.formId = formId;
        this.userRole = userRole;
    }
}
