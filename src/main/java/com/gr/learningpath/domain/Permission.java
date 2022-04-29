package com.gr.learningpath.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {

    ALL_VIEW("ALL_VIEW"),
    ALL_EDIT("ALL_EDIT");
    private final String title;

}
