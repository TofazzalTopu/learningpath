package com.gr.learningpath.facade.user;

import com.gr.learningpath.api.*;
import com.gr.learningpath.api.request.connection.ConnectionResponse;
import com.gr.learningpath.api.request.user.RegisterRequest;
import com.gr.learningpath.api.request.user.UserProfileRequest;
import com.gr.learningpath.api.response.course.CourseResponseShort;
import com.gr.learningpath.api.response.user.MentorResponse;
import com.gr.learningpath.api.response.user.UserResponse;
import com.gr.learningpath.domain.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public interface UserFacade {
    UserResponse updateUser(@Nonnull String authUsername, @Nonnull UserProfileRequest userProfileRequest, MultipartFile profilePhoto, MultipartFile coverPhoto) throws IOException;

    List<DropDown> getMemberList();

    UserProfileRequest fetchUserProfileFormData(String authUsername);

    List<ImageResponse> fetchUserImages(@Nonnull String authUsername);

    void deleteImage(ImageModel imageModel);

    UserResponse register(RegisterRequest registerForm);

    List<DropDown> getCourseList();

    List<DropDown> getChapterList();

    void connectWithUser(Long userId, Long studentId) throws IllegalAccessException;

    void deleteUserConnection(Long connectionId);

    List<ConnectionResponse> getUserConnections();

    List<MentorResponse> getMentors();

    List<MentorResponse> getMentorsByCourse(Long courseId);

    List<CourseResponseShort> getCoursesByMentor(Long mentorId);
}
