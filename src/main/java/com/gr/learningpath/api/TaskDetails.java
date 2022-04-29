package com.gr.learningpath.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TaskDetails {
    private Long id;
    private String title;
    private String chapterName;
    private String sessionTitle;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String description;
    private String docs;
}
