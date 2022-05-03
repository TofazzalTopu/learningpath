package com.gr.learningpath.controller.user;

import com.gr.learningpath.api.request.user.LoginRequest;
import com.gr.learningpath.api.response.user.TokenResponse;
import com.gr.learningpath.config.secuirty.JwtTokenUtil;
import com.gr.learningpath.service.SecurityUserDetailsService;
import com.gr.learningpath.validator.LoginValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.validation.ValidationException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    @Nonnull
    private final LoginValidator loginValidator;
    private final SecurityUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Nonnull
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public TokenResponse login(@RequestBody LoginRequest form, BindingResult result) {
        loginValidator.validate(form, result);
        if (result.hasErrors()) {
            throw new ValidationException();
        }
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(form.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return TokenResponse.builder().token(token).build();
    }
}
