package com.gr.learningpath.api.response.course;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public
class CourseImageResponse {

    private String imageName;
    private String imageType;
    private byte[] imageBinary;

}
