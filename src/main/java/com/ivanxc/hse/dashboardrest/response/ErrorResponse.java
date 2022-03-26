package com.ivanxc.hse.dashboardrest.response;

import java.util.List;
import lombok.Data;

@Data
public class ErrorResponse {
    private final String message;
    private final List<String> details;
}