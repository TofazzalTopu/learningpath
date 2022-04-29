package com.gr.learningpath.api.response.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DocumentResponse {

    String docName;
    String docFileType;
    String docContentCategory;
    byte[] docBinary;

}
