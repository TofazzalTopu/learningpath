package com.gr.learningpath.mapper.group;

import com.gr.learningpath.api.ImageResponse;
import com.gr.learningpath.api.response.group.GroupResponse;
import com.gr.learningpath.domain.Member;
import com.gr.learningpath.domain.Student;
import com.gr.learningpath.domain.User;
import com.gr.learningpath.domain.group.Group;
import com.gr.learningpath.mapper.Mapper;
import com.gr.learningpath.mapper.MapperRegistry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GroupMapper {
    @NonNull
    private final MapperRegistry mapperRegistry;

    @PostConstruct
    private void registerMappers() {
        mapperRegistry.addMapper(Group.class, GroupResponse.class, groupToGroupDetailsMapper());

    }

    private Mapper<Group, GroupResponse> groupToGroupDetailsMapper() {
        return group -> GroupResponse.builder()
                .groupImage(ImageResponse.builder().type(group.getImageType())
                        .name(group.getImageName())
                        .imageBinary(group.getImageBinary())
                        .build())
                .id(group.getId())
                .courseTitle(group.getCourse().getTitle())
                .groupName(group.getName())
                .createDate(group.getCreatedAt().toString())
                .members(group.getMembers()
                        .stream()
                        .map(Member::getStudent)
                        .map(Student::getUser)
                        .map(User::getDisplayName)
                        .collect(Collectors.toList()))
                .departmentName(group.getDepartment().getName())
                .build();
    }

}
