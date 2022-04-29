package com.gr.learningpath.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SessionDetails {
    private Long id;
    private Integer duration;
    private String docs;
    private ImageResponse groupImage;
    private String chapterTitle;
    private LocalDate createDate;
    private List<String> members;
    private String departmentName;
}


