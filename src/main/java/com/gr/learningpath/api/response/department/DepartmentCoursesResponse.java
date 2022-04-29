package com.gr.learningpath.api.response.department;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class DepartmentCoursesResponse {
    private Long courseId;
    private String name;
    private Integer semester;
}
