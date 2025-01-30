package com.project.ITAM.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN) // Returns 403
public class UserNotAuthorizedException extends RuntimeException {
    public UserNotAuthorizedException(String action) {
        super("You are not authorized to " + action);
    }
}
