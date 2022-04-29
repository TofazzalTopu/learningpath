package com.gr.learningpath.validator.course;

import com.gr.learningpath.api.request.course.CourseContentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Nonnull;

@Component
@RequiredArgsConstructor
public class CourseContentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CourseContentRequest.class);
    }

    @Override
    public void validate(@Nonnull Object target, @Nonnull Errors errors) {
        CourseContentRequest courseContentForm = (CourseContentRequest) target;

        if (courseContentForm.getCourseId() == null){
            errors.rejectValue("courseId", "courseId.is.required", "courseId is required");
        }
    }
}

