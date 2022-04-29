package com.gr.learningpath.service;

import com.gr.learningpath.api.response.user.UserResponse;
import com.gr.learningpath.domain.*;
import com.gr.learningpath.domain.course.Chapter;
import com.gr.learningpath.domain.course.Course;
import com.gr.learningpath.exceptions.EntityAlreadyExistsException;
import com.gr.learningpath.exceptions.EntityNotFoundException;
import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.repositories.*;
import com.gr.learningpath.repositories.course.ChapterRepository;
import com.gr.learningpath.repositories.course.CourseRepository;
import com.gr.learningpath.repositories.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private final ImageRepository imageRepository;
    private final CourseRepository courseRepository;
    private final ChapterRepository chapterRepository;
    private final MapperRegistry mapperRegistry;
    private final FollowRepository followRepository;

    @Override
    public UserResponse findUserDetailsByUsername(@Nonnull final String username) {
        return
                mapperRegistry.getMapper(User.class, UserResponse.class).map(
                        userRepository
                                .findByUsername(username)
                                .orElseThrow(
                                        () -> new UsernameNotFoundException("Username not found")
                                ));

    }

    @Override
    public User findUserByUsername(@Nonnull final String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with  " + username + " was not found in the database.")
                );
    }

    @Transactional
    @Override
    public User updateUser(@Nonnull User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUser(@Nonnull Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUser(@Nonnull Long id) {
        return findUser(id).orElseThrow(() -> new EntityNotFoundException(User.class));
    }

    @Override
    public Optional<Student> findStudentByUserId(@Nonnull Long id) {
        return studentRepository.findByUser_Id(id);
    }

    @Override
    public Student getStudentByUserId(@Nonnull Long id) {
        return findStudentByUserId(id).orElseThrow(() -> new EntityNotFoundException(Student.class));
    }

    @Transactional
    @Override
    public Student saveStudent(@Nonnull Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<ImageModel> findImage(@Nonnull Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public ImageModel getImage(@Nonnull Long id) {
        return findImage(id).orElseThrow(() -> new EntityNotFoundException(ImageModel.class));
    }

    @Override
    public List<ImageModel> getImagesByUsername(@Nonnull String authUsername) {
        return imageRepository.findAllByUser_Username(authUsername);
    }

    @Transactional
    @Override
    public List<ImageModel> saveImages(@Nonnull List<ImageModel> images) {
        return imageRepository.saveAll(images);
    }

    @Transactional
    @Override
    public void deleteImage(final ImageModel imageModel) {
        imageRepository.delete(imageModel);
    }

    @Transactional
    @Override
    public User saveUser(@Nonnull final User user) {
        final boolean exist = userRepository.existsByUsername(user.getUsername());
        if (exist) {
            throw new EntityAlreadyExistsException(User.class);
        }

        return userRepository.save(user);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }

    @Transactional
    @Override
    public void saveConnection(Long userId, Long studentId) {

        User fromUser = userRepository.findById(userId).get();
        Follow follow = Follow.builder().fromUser(fromUser).toUser(studentId).build();

        followRepository.save(follow);
    }

    @Transactional
    @Override
    public void deleteConnection(@Nonnull final Long connectionId) {
        Follow follow = followRepository.findById(connectionId).orElseThrow(() -> new EntityNotFoundException(User.class));
        followRepository.delete(follow);
    }

    @Override
    public List<User> findConnections(@Nonnull final Long userId) {
        final Optional<List<Follow>> connections = followRepository.findAllByFromUser(userId);

        List<User> connectionsList = new ArrayList<>();

        if (!connections.isEmpty()) {
            final List<Long> toUsersIds = connections.get().stream().map(s -> s.getToUser())
                    .collect(Collectors.toList());
            connectionsList = userRepository.findAllById(toUsersIds);
        }

        return connectionsList;
    }

    @Override
    public Long checkIfConnectionExists(@Nonnull final Long fromUserId, @Nonnull final Long toUserId) {
        final Optional<Follow> connection = followRepository.findByFromUserAndToUser(fromUserId, toUserId);

        if (!connection.isEmpty()) {
            return connection.get().getId();
        }
        return null;
    }

    @Override
    public List<User> getMentors(){

        Optional<List<User>> mentors = userRepository.fetchMentors();

        if (!mentors.isEmpty()) {
            return mentors.get();
        }
        return null;
    }

    @Override
    public List<User> getMentorsByCourse(Long courseId){

        Optional<List<User>> mentors = userRepository.fetchMentorsByCourse(courseId);

        if (!mentors.isEmpty()) {
            return mentors.get();
        }
        return null;
    }

    @Override
    public List<Course> getCoursesByMentor(Long mentorId){

        Optional<List<Course>> courses = courseRepository.fetchCoursesByMentor(mentorId);

        if (!courses.isEmpty()) {
            return courses.get();
        }
        return null;
    }


}
