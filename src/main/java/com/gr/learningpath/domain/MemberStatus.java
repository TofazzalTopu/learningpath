package com.gr.learningpath.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum MemberStatus {

    ACTIVE(1),
    INACTIVE(0);

    private final int id;

    public static MemberStatus getById(Integer id) {
        return Arrays.stream(MemberStatus.values())
                .filter(status -> status.getId() == id)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
