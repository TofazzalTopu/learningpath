package com.gr.learningpath.api.request.user;

import com.gr.learningpath.api.DropDown;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class RegistrationRequest {
    private String username;
    private String password;
    private DropDown identity;

}
