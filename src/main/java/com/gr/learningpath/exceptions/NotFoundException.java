package com.gr.learningpath.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -6945148334740191789L;

    private NotFoundException(String message) {
        super(message);
    }
}
