package com.gr.learningpath.api.request.user;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class LoginRequest {
    private String username;
    private String password;
    @Builder.Default
    private Boolean rememberMe = false;

}


