package com.gr.learningpath.service;

import com.gr.learningpath.api.request.user.LearningPathUser;
import com.gr.learningpath.domain.Permission;
import com.gr.learningpath.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class SecuritySupport {

    public LearningPathUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LearningPathUser) {
            return (LearningPathUser) principal;
        }
        throw new RuntimeException();
    }

    public User getUser() {
        return getCurrentUser().getUser();
    }


    public boolean isAuthorized(@Nonnull final Permission permission) {
        return getCurrentUser().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(permission.getTitle()));
    }
}
