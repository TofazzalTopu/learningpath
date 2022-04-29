package com.gr.learningpath.api.request.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class UserProfileRequest {
    private String username;
    private Long id;
    private String firstName;
    private String lastName;
    private String screenName;
    private String department;
    private String livesIn;
    private String contactNumber;
    private String facebookProfileUrl;
    private String skypeId;
    private String aboutMe;
    private String coursesOfInterest;
}