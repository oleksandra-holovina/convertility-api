package com.convertility.exception;

public class AuthResponseException extends RuntimeException {
    public AuthResponseException() {
        super("Authentication Exception");
    }
}
