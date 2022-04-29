package com.gr.learningpath.api.request.course;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class CourseContentRequest {
    private Long id;
    private Long courseId;
    private String relativeUrl;
}
