package com.gr.learningpath.validator.course;

import com.gr.learningpath.api.request.course.ChapterRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Nonnull;

@Component
@RequiredArgsConstructor
public class ChapterValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ChapterRequest.class);
    }
    @Override
    public void validate(@Nonnull Object target, @Nonnull Errors errors) {
       ChapterRequest chapterRequest = (ChapterRequest) target;

//        if (StringUtils.isBlank(chapterForm.getRelativeUrl())) {
//            errors.rejectValue("relativeUrl", "relativeUrl.is.required", "relativeUrl is required");
//        }
        if (StringUtils.isBlank(chapterRequest.getChapterTitle())) {
            errors.rejectValue("chapterTitle", "chapterTitle.is.required", "chapterTitle is required");
        }

    }
}
