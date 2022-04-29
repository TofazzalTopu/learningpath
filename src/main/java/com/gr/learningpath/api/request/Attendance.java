package com.gr.learningpath.api.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter

public class Attendance {
    private Long studentId;
    private Boolean present;
    private Integer evaluationValue;
}
