package com.gr.learningpath.domain.course;

import com.gr.learningpath.domain.document.DocumentType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseChapterDTO {
    private Long recordId;
    private String chapterTitle;
    private String relativeUrl;
    private Integer effortPoints;
    private DocumentType docContentCategory;
    private String docName;
    private String docFileType;
    private byte[] data;

}


