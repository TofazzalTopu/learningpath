package com.gr.learningpath.api.request;

import com.gr.learningpath.api.DropDown;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class SessionRequest {
    private Long id;
    private Long groupId;
    private Long courseId;
    private String title;
    private LocalDate date;
    private Integer duration;
    private String description;
    private DropDown chapter;
    private List<Attendance> attendances;

}



