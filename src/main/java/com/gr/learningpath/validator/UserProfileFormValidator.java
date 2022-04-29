package com.gr.learningpath.validator;

import com.gr.learningpath.api.request.user.UserProfileRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Nonnull;

@Component
@RequiredArgsConstructor
public class UserProfileFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserProfileRequest.class);
    }

    @Override
    public void validate(@Nonnull Object target, @Nonnull Errors errors) {
        UserProfileRequest userProfileRequest = (UserProfileRequest) target;

        if (StringUtils.isBlank(userProfileRequest.getFirstName())) {
            errors.rejectValue("firstName", "firstName.is.required", "FirstName is required");
        }
        if (StringUtils.isBlank(userProfileRequest.getLastName())) {
            errors.rejectValue("lastName", "lastName.is.required", "LastName is required");
        }
        if (StringUtils.isBlank(userProfileRequest.getScreenName())) {
            errors.rejectValue("screenName", "screenName.is.required", "ScreenName is required");
        }

    }
}
