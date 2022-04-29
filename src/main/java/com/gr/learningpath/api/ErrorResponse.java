package com.gr.learningpath.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.Problem;

@Builder
@Getter
@Setter
public class ErrorResponse {
    private Problem error;
}
