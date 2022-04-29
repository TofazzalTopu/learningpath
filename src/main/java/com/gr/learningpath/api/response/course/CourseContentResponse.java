package com.gr.learningpath.api.response.course;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CourseContentResponse {

    private Long Id;
    private String relativeUrl;
    List<CourseContentDocumentResponse> files;

}

