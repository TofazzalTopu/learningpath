package com.gr.learningpath.api.request.course;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class PublishCourseRequest implements Serializable {

    private Long courseId;
    private Boolean isPrivate;

}
