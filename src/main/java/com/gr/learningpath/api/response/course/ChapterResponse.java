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
public class ChapterResponse {

    private Long courseId;
    private Long chapterId;
    private String chapterTitle;
    private String relativeUrl;
    private Integer effortPoints;
    private List<DocumentResponse> files;

}




