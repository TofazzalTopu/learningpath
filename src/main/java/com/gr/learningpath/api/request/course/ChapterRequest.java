package com.gr.learningpath.api.request.course;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class ChapterRequest {
    private Long id;
    private Long courseId;
    private String chapterTitle;
    private String relativeUrl;
    private Integer effortPoints;
}
