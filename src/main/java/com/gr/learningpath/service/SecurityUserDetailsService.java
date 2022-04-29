package com.gr.learningpath.service;

import com.gr.learningpath.api.request.user.LearningPathUser;
import com.gr.learningpath.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@Component("userDetailsService")
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    @Nonnull
    private final UserService userService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(@Nonnull String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        return new LearningPathUser(user);
    }
}
