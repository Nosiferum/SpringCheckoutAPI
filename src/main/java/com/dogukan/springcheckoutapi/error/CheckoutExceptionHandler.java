package com.dogukan.springcheckoutapi.error;

import com.dogukan.springcheckoutapi.response.CheckoutErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CheckoutExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CheckoutErrorResponse> handleException(CheckoutEntityNotFoundException exception) {
        CheckoutErrorResponse error = new CheckoutErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CheckoutErrorResponse> handleException(BadRequestException exception) {
        CheckoutErrorResponse error = new CheckoutErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //TODO add specific exceptions instead of this general one
    @ExceptionHandler
    public ResponseEntity<CheckoutErrorResponse> handleException(Exception exception) {
        CheckoutErrorResponse error = new CheckoutErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}