package com.ivanxc.hse.dashboardrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class HaveNoAccessToFormException extends RuntimeException {
    public HaveNoAccessToFormException(String message) {
        super(message);
    }
}
