package com.gr.learningpath.api.response.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CourseResponseShort {

    private Long id;
    private String title;
    private String department;

}
