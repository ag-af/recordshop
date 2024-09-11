package com.northcoders.recordshop.exception;

public class CustomException extends RuntimeException {
    String message;

    public CustomException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
