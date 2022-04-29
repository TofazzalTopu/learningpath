package com.gr.learningpath.api.response.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class MentorResponse {

    Long userId;
    String username;

}
