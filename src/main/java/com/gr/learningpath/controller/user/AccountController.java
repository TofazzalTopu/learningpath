package com.gr.learningpath.controller.user;

import com.gr.learningpath.api.response.user.UserResponse;
import com.gr.learningpath.domain.User;
import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.service.SecuritySupport;
import com.gr.learningpath.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class AccountController {
    @Nonnull
    private final UserService userService;
    @Nonnull
    private final SecuritySupport securitySupport;
    private final MapperRegistry mapperRegistry;


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public UserResponse profile() {
        return mapperRegistry.getMapper(User.class, UserResponse.class).map(securitySupport.getUser());
    }


}
