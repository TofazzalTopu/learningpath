package com.gr.learningpath.validator;

import com.gr.learningpath.api.request.TaskRequest;
import com.gr.learningpath.api.request.course.CourseRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Nonnull;

@Component
@RequiredArgsConstructor
public class TaskValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CourseRequest.class);
    }

    @Override
    public void validate(@Nonnull Object target, @Nonnull Errors errors) {
        TaskRequest taskRequest = (TaskRequest) target;
        if (StringUtils.isBlank(taskRequest.getTitle())) {
            errors.rejectValue("title", "title.is.required", "Title is required");
        }

        if (taskRequest.getChapter() == null) {
            errors.rejectValue("chapter", "chapter.is.required", "Chapter is required");
        }

        if (StringUtils.isEmpty(taskRequest.getDescription())) {
            errors.rejectValue("description", "description.is.required", "Description is required");

        }

        if (taskRequest.getStartDate() == null) {
            errors.rejectValue("startDate", "startDate.is.required", "Start Date is required");
        }

        if (taskRequest.getDueDate() == null) {
            errors.rejectValue("dueDate", "dueDate.is.required", "Due Date is required");
        }

        if (taskRequest.getSession() == null) {
            errors.rejectValue("session", "session.is.required", "Session is required");
        }
    }
}
