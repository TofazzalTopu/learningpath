package com.gr.learningpath.api.request.user;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class RegisterRequest {
    private String username;
    private String password;

}
