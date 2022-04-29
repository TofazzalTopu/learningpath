package com.gr.learningpath.api.request.group;

import com.gr.learningpath.api.DropDown;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class GroupRequest {
    private Long id;
    private String name;
    private DropDown department;
    private DropDown course;
    private String comment;
    private List<DropDown> members; //TODO: it is student id @ creation and member id at edit.
}




