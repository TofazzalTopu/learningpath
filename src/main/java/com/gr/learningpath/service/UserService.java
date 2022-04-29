package com.gr.learningpath.service;


import com.gr.learningpath.api.response.user.UserResponse;
import com.gr.learningpath.domain.*;
import com.gr.learningpath.domain.course.Chapter;
import com.gr.learningpath.domain.course.Course;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponse findUserDetailsByUsername(@Nonnull final String username);

    User findUserByUsername(@Nonnull final String username);

    User updateUser(@Nonnull final User user);

    User getUser(@Nonnull Long id);

    Optional<User> findUser(@Nonnull Long id);

    Optional<Student> findStudentByUserId(@Nonnull Long id);

    Student getStudentByUserId(@Nonnull Long id);

    Student saveStudent(@Nonnull Student student);

    User saveUser(@Nonnull User user);

    Optional<ImageModel> findImage(@Nonnull Long id);

    ImageModel getImage(@Nonnull Long id);

    List<ImageModel> getImagesByUsername(@Nonnull String authUsername);

    List<ImageModel> saveImages(@Nonnull List<ImageModel> images);

    void deleteImage(ImageModel imageModel);

    List<Student> getAllStudents();

    List<Course> getAllCourses();

    List<Chapter> getAllChapters();

    void saveConnection(Long userId, Long studentId);

    void deleteConnection(Long connectionId);

    List<User> findConnections(Long id);

    Long checkIfConnectionExists(Long fromUserId, Long toUserId);

    List<User> getMentors();

    List<User> getMentorsByCourse(Long courseId);

    List<Course> getCoursesByMentor(Long mentorId);
}
