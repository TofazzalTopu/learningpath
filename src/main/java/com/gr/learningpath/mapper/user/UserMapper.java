package com.gr.learningpath.mapper.user;

import com.gr.learningpath.api.DropDown;
import com.gr.learningpath.api.request.connection.ConnectionResponse;
import com.gr.learningpath.api.request.user.UserProfileRequest;
import com.gr.learningpath.api.response.user.MentorResponse;
import com.gr.learningpath.api.response.user.UserResponse;
import com.gr.learningpath.domain.Student;
import com.gr.learningpath.domain.User;
import com.gr.learningpath.mapper.Mapper;
import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class UserMapper {
    @Nonnull
    private final MapperRegistry mapperRegistry;
    @Nonnull
    private final UserService userService;

    @PostConstruct
    private void registerMappers() {
        mapperRegistry.addMapper(User.class, UserResponse.class, userToUserDetailsMapper());
        mapperRegistry.addMapper(UserProfileRequest.class, User.class, userProfileFormUserMapper());
        mapperRegistry.addMapper(User.class, UserProfileRequest.class, userToUserProfileForm());
        mapperRegistry.addMapper(Student.class, DropDown.class, studentToMemberMapper());
        mapperRegistry.addMapper(User.class, ConnectionResponse.class, userToConnectionResponseMapper());
        mapperRegistry.addMapper(User.class, MentorResponse.class, userMentorResponseMapper());
    }

    private Mapper<User, UserResponse> userToUserDetailsMapper() {
        return user -> UserResponse
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .screenName(user.getDisplayName())
                .joinedIn(user.getCreatedAt().toLocalDateTime())
                .email(user.getUsername())
//                .department(user.isStudent !=null && user.isStudent && user.getStudent().getDepartment() != null
//                        ? mapperRegistry.getMapper(Department.class, DepartmentResponse.class).map(user.getStudent().getDepartment()) : null)
                .username(user.getUsername())
                .active(user.getActive())
                .aboutMe(user.getAboutMe())
                .contactNumber(user.getContactNumber())
                .coursesOfInterest(user.getCoursesOfInterest())
                .facebookUrl(user.getFacebookUrl()).firstName(user.getFirstName()).livesIn(user.getLivesIn())
                .skypeId(user.getSkypeId()).registrationDate(user.getRegistrationDate())
                .contactNumber(user.getContactNumber())
                .roles(user.getRoles())
                .permissions(user.getPermissions())
                .isStudent(user.getIsStudent())
                .build();
    }

    private Mapper<UserProfileRequest, User> userProfileFormUserMapper() {
        return form -> {
            final User.UserBuilder<?, ?> builder = userService.getUser(form.getId()).toBuilder();
            builder.lastName(form.getLastName());
            builder.firstName(form.getFirstName());
            builder.aboutMe(form.getAboutMe());
            builder.contactNumber(form.getContactNumber());
            builder.facebookUrl(form.getFacebookProfileUrl());
            builder.skypeId(form.getSkypeId());
            builder.coursesOfInterest(form.getCoursesOfInterest());
            builder.livesIn(form.getLivesIn());
            builder.displayName(form.getScreenName());
            return builder.build();
        };
    }

    private Mapper<User, UserProfileRequest> userToUserProfileForm() {
        return user -> UserProfileRequest.builder()
                .username(user.getUsername())
                .id(user.getId())
                .aboutMe(user.getAboutMe())
                .contactNumber(user.getContactNumber())
                .coursesOfInterest(user.getCoursesOfInterest())
                .facebookProfileUrl(user.getFacebookUrl())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .skypeId(user.getSkypeId())
                .screenName(user.getDisplayName())
                .livesIn(user.getLivesIn())
                .build();
    }

    private Mapper<Student, DropDown> studentToMemberMapper() {
        return student -> DropDown.builder()
                .label(student.getUser().getUsername())
                .value(student.getId())
                .build();
    }

    private Mapper<User, ConnectionResponse> userToConnectionResponseMapper() {
        return user -> ConnectionResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    private Mapper<User, MentorResponse> userMentorResponseMapper() {
        return user -> MentorResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }
}
