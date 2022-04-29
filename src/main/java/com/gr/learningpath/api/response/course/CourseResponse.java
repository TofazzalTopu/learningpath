package com.gr.learningpath.api.response.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private Integer semester;
    private CourseImageResponse image;
    private CourseContentResponse courseContent;
    private String department;
    private String relatedCourse;
    private List<ChapterResponse> chapters;
    private CoursePublicationResponse coursePublication;

}
