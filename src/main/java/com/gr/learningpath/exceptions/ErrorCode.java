package com.gr.learningpath.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1017),
    VALIDATION_FAILED(2001),
    NOT_ALLOWED(2002),
    CANNOT_DELETE(2005),
    CANNOT_RENAME(2006),
    USERNAME_ALREADY_EXIST(2007);

    @JsonValue
    private final int statusCode;

}
