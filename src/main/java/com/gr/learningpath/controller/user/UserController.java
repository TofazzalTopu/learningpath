package com.gr.learningpath.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.learningpath.api.ImageResponse;
import com.gr.learningpath.api.request.user.UserProfileRequest;
import com.gr.learningpath.api.response.course.CourseResponseShort;
import com.gr.learningpath.api.response.user.MentorResponse;
import com.gr.learningpath.api.response.user.UserResponse;
import com.gr.learningpath.facade.user.UserFacade;
import com.gr.learningpath.security.SecurityUtils;
import com.gr.learningpath.validator.UserProfileFormValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserFacade userFacade;
    private final UserProfileFormValidator profileValidator;

    @GetMapping
    public UserProfileRequest fetchUserFormData() {
        final String authUsername = SecurityUtils.getLoggedInUser().orElseThrow(IllegalStateException::new);
        return userFacade.fetchUserProfileFormData(authUsername);
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public UserResponse UploadImage(@RequestParam("profilePhoto") MultipartFile profilePhoto,
                                    @RequestParam("coverPhoto") MultipartFile coverPhoto,
                                    @RequestPart("userProfileForm") String userprofileJson,
                                    BindingResult result
    ) throws IOException {
        UserProfileRequest userProfileRequest = new UserProfileRequest();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userProfileRequest = objectMapper.readValue(userprofileJson, UserProfileRequest.class);
        } catch (IOException err) {
            log.error(err.getMessage());
        }
        if (profilePhoto == null || coverPhoto == null) {
            throw new IllegalStateException();
        }

        profileValidator.validate(userProfileRequest, result);
        final String authUserName = SecurityUtils.getLoggedInUser().orElseThrow(IllegalStateException::new);
        return userFacade.updateUser(authUserName, userProfileRequest, profilePhoto, coverPhoto);
    }

    @GetMapping(path = {"/images"})
    public List<ImageResponse> getImage() {
        final String authUsername = SecurityUtils.getLoggedInUser().orElseThrow(IllegalStateException::new);
        return userFacade.fetchUserImages(authUsername);
    }

    @GetMapping(path = {"/mentors"})
    public List<MentorResponse> getUsers(){
        return userFacade.getMentors();
    }

    @GetMapping(path = {"/mentors/{courseId}"})
    public List<MentorResponse> getMentorsByCourse(@PathVariable final Long courseId){
        return userFacade.getMentorsByCourse(courseId);
    }

    @GetMapping(path = {"/courses/{mentorId}"})
    public List<CourseResponseShort> getCoursesByMentor(@PathVariable final Long mentorId){
        return userFacade.getCoursesByMentor(mentorId);
    }
}
