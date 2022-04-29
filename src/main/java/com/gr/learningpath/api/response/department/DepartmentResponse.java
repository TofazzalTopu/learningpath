package com.gr.learningpath.api.response.department;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class DepartmentResponse {

    private Long id;
    private String title;
}
