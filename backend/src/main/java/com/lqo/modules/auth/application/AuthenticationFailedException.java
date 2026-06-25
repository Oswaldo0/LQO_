package com.lqo.modules.auth.application;

public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException() {
        super("Invalid username or password");
    }
}
