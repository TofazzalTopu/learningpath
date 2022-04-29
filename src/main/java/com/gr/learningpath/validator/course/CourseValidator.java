package com.gr.learningpath.validator.course;

import com.gr.learningpath.api.request.course.CourseRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Nonnull;

@Component
@RequiredArgsConstructor
public class CourseValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CourseRequest.class);
    }

    @Override
    public void validate(@Nonnull Object target, @Nonnull Errors errors) {
        CourseRequest courseRequest = (CourseRequest) target;

        if (StringUtils.isBlank(courseRequest.getTitle())) {
            errors.rejectValue("title", "title.is.required", "Title is required");
        }
    }
}



