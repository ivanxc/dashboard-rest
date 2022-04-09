package com.ivanxc.hse.dashboardrest.jwt;

import lombok.Data;

@Data
public class JwtRequest {
    private String login;
    private String password;
}
