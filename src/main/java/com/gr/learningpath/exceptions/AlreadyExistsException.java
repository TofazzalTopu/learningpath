package com.gr.learningpath.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = -6945148334740191789L;

    private AlreadyExistsException(String message) {
        super(message);
    }
}
