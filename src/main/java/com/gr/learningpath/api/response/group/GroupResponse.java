package com.gr.learningpath.api.response.group;

import com.gr.learningpath.api.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GroupResponse {
    private Long id;
    private String groupName;
    private ImageResponse groupImage;
    private String courseTitle;
    private String createDate;
    private List<String> members;
    private String departmentName;
}


