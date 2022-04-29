package com.gr.learningpath.validator;

import com.gr.learningpath.api.request.user.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Nonnull;

@Component
@RequiredArgsConstructor
public class LoginValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(LoginRequest.class);
    }

    @Override
    public void validate(@Nonnull Object target, @Nonnull Errors errors) {
        LoginRequest form = (LoginRequest) target;

        if (StringUtils.isBlank(form.getUsername())) {
            errors.rejectValue("username", "username.is.required", "Username is required");
        } else if (form.getUsername().length() > 50) {
            errors.rejectValue("username", "username.maxLength", "Username can be maximum 50 characters long");
        }

        if (StringUtils.isBlank(form.getPassword())) {
            errors.rejectValue("password", "password.is.required", "Password is required");
        } else if (form.getPassword().length() > 50) {
            errors.rejectValue("password", "password.maxLength", "Password can be maximum 50 characters long");
        }
    }
}
