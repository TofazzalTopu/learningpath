package com.gr.learningpath.validator;

import com.gr.learningpath.api.request.group.GroupRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Nonnull;

@Component
@RequiredArgsConstructor
public class GroupValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(GroupRequest.class);
    }

    @Override
    public void validate(@Nonnull Object target, @Nonnull Errors errors) {
        GroupRequest groupRequest = (GroupRequest) target;
        if (StringUtils.isBlank(groupRequest.getName())) {
            errors.rejectValue("name", "name.is.required", "Name is required");
        }

        if (groupRequest.getDepartment() == null) {
            errors.rejectValue("department", "department.is.required", "Department is required");
        }

        if (groupRequest.getCourse() == null) {
            errors.rejectValue("course", "course.is.required", "Course is required");
        }

        if (groupRequest.getMembers() == null || groupRequest.getMembers().size() == 0) {
            errors.rejectValue("members", "members.are.required", "Members are required");
        }
    }
}

