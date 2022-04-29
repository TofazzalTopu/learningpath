package com.gr.learningpath.api.response.user;


import com.gr.learningpath.domain.Permission;
import com.gr.learningpath.domain.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String aboutMe;
    private String firstName;
    private String lastName;
    private String username;
    private LocalDateTime joinedIn;
    private String screenName;
    @Builder.Default
    private Boolean active = false;
    private Boolean isStudent;
    private String department;
    private String email;
    private Set<Role> roles;
    private Long version;
    private String livesIn;
    private LocalDate registrationDate;
    private String contactNumber;
    private String facebookUrl;
    private String skypeId;
    private String coursesOfInterest;
    private Set<Permission>permissions;

}
