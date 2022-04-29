package com.gr.learningpath.api.request;

import com.gr.learningpath.api.DropDown;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class TaskRequest {
    private Long id;
    private Long groupId;
    private String title;
    private DropDown session;
    private DropDown chapter;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String description;
}
