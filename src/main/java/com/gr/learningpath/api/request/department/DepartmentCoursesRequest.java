package com.gr.learningpath.api.request.department;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class DepartmentCoursesRequest {
    private Long departmentId;

}
