package com.gr.learningpath.api.request.user;

import com.gr.learningpath.domain.Permission;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Collectors;

@Getter
public class LearningPathUser extends User {

    private static final long serialVersionUID = 437913381870118006L;
    private final com.gr.learningpath.domain.User user;

    public LearningPathUser(com.gr.learningpath.domain.User user) {
        super(
                user.getUsername(),
                user.getPassword(),
                user.getPermissions().stream()
                        .map(Permission::getTitle)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet())
        );
        this.user = user;
    }
}
