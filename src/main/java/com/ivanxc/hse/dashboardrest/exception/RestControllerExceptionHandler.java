package com.ivanxc.hse.dashboardrest.exception;

import com.ivanxc.hse.dashboardrest.response.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("Incorrect request", details), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HaveNoAccessToFormException.class)
    public ResponseEntity<ErrorResponse> handleHaveNoAccessToFormException(HaveNoAccessToFormException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("Incorrect request", details), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HaveNoEnoughRightsException.class)
    public ResponseEntity<ErrorResponse> handleHaveNoEnoughRightsException(HaveNoEnoughRightsException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("Incorrect request", details), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("Incorrect request", details), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonConvertationException.class)
    public ResponseEntity<ErrorResponse> handleJsonConvertationException(JsonConvertationException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("Incorrect request", details), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("Incorrect request", details), HttpStatus.BAD_REQUEST);
    }

}
