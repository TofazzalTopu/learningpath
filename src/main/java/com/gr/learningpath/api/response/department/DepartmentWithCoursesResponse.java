package com.gr.learningpath.api.response.department;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class DepartmentWithCoursesResponse {

    private Long id;
    private String title;
    private List<DepartmentCoursesResponse> departmentCoursesResponseList;
}
