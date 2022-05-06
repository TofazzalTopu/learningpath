package com.gr.learningpath.controller.user;

import com.gr.learningpath.api.request.user.RegisterRequest;
import com.gr.learningpath.api.response.user.UserResponse;
import com.gr.learningpath.facade.user.UserFacade;
import com.gr.learningpath.service.SecuritySupport;
import com.gr.learningpath.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;

@CrossOrigin
@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {
    @Nonnull
    private final UserService userService;
    @Nonnull
    private final SecuritySupport securitySupport;
    private final UserFacade userFacade;


    @PostMapping
    public UserResponse register(@RequestBody RegisterRequest registerRequest) {
        return userFacade.register(registerRequest);
    }

}
