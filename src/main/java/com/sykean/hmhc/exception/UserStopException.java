package com.sykean.hmhc.exception;

import org.springframework.security.core.AuthenticationException;

public class UserStopException extends AuthenticationException {

    public UserStopException(String msg) {
        super(msg);
    }
}
