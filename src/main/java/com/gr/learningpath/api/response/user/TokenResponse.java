package com.gr.learningpath.api.response.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TokenResponse {
    private String token;
}
