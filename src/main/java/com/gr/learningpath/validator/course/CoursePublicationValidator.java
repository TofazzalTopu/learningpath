package com.gr.learningpath.validator.course;

import com.gr.learningpath.api.request.course.CourseRequest;
import com.gr.learningpath.api.request.course.PublishCourseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Nonnull;

@Component
@RequiredArgsConstructor
public class CoursePublicationValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CourseRequest.class);
    }

    @Override
    public void validate(@Nonnull Object target, @Nonnull Errors errors) {
        PublishCourseRequest publishCourseRequest = (PublishCourseRequest) target;

        if (publishCourseRequest.getCourseId() == null) {
            errors.rejectValue("courseId", "id.is.required", "Course Id is required");
        }

        if (publishCourseRequest.getIsPrivate() == null) {
            errors.rejectValue("isPrivate", "id.is.required", "Visibility info is required");
        }
    }
}



