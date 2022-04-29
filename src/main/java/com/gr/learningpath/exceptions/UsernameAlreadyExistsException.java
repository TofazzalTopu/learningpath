package com.gr.learningpath.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsernameAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = -6945148334740191789L;

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
