package com.dogukan.springcheckoutapi.error;

public class CheckoutEntityNotFoundException extends RuntimeException {
    public CheckoutEntityNotFoundException(String message) {
        super(message);
    }
}
