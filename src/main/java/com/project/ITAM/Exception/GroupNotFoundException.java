package com.project.ITAM.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Sends 404 instead of 400
public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(Long groupId) {
        super( groupId + " not found");
    }
}
