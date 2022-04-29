package com.gr.learningpath.api.response.course;

import com.gr.learningpath.domain.document.DocumentType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseContentDocumentResponse {

    private String docName;
    private String docFileType;
    private DocumentType docContentCategory;
    private byte[] data;

}
