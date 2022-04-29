package com.gr.learningpath.api.request.course;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class CourseRequest {
    private Long id;
    private Long userId;
    private String title;
    private Long relatedCourse;
    private String description;
    private Long departmentId;
    private Integer semester;
    private String courseImageName;
    private String imageType;
    private byte[] imageBinary;

}
