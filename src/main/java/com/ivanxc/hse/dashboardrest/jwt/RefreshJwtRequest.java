package com.ivanxc.hse.dashboardrest.jwt;

import lombok.Data;

@Data
public class RefreshJwtRequest {
    public String refreshToken;
}