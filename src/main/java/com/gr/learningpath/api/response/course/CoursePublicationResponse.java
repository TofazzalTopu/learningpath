package com.gr.learningpath.api.response.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CoursePublicationResponse {

    Boolean isPrivate;

}
