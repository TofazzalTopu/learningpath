package com.gr.learningpath.facade.user;

import com.gr.learningpath.api.DropDown;
import com.gr.learningpath.api.ImageResponse;
import com.gr.learningpath.api.request.connection.ConnectionResponse;
import com.gr.learningpath.api.request.user.RegisterRequest;
import com.gr.learningpath.api.request.user.UserProfileRequest;
import com.gr.learningpath.api.response.course.CourseResponseShort;
import com.gr.learningpath.api.response.user.MentorResponse;
import com.gr.learningpath.api.response.user.UserResponse;
import com.gr.learningpath.domain.*;
import com.gr.learningpath.domain.course.Chapter;
import com.gr.learningpath.domain.course.Course;
import com.gr.learningpath.exceptions.EntityNotFoundException;
import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.repositories.RoleRepository;
import com.gr.learningpath.repositories.UserRepository;
import com.gr.learningpath.security.SecurityUtils;
import com.gr.learningpath.service.UserService;
import com.gr.learningpath.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static com.gr.learningpath.config.AuthoritiesConstants.SUPER_ADMIN_CODE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFacadeImpl implements UserFacade {
    private final UserService userService;
    private final MapperRegistry mapperRegistry;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final DepartmentService departmentService;
    private final UserRepository userRepository;

    public static byte[] compressBytes(final byte[] data) {
        final Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        final byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            final int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (final IOException ignored) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(final byte[] data) {
        final Inflater inflater = new Inflater();
        inflater.setInput(data);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        try {
            final byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                final int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (final IOException | DataFormatException ignored) {
        }
        return outputStream.toByteArray();
    }

    @Transactional
    @Override
    public UserResponse updateUser(
            @Nonnull final String authUsername,
            @Nonnull final UserProfileRequest userProfileRequest,
            @Nonnull final MultipartFile profilePhoto,
            @Nonnull final MultipartFile coverPhoto
    ) throws IOException {
        final User userToBeUpdated = mapperRegistry.getMapper(UserProfileRequest.class, User.class).map(userProfileRequest);
        if (userToBeUpdated.isStudent && userProfileRequest.getDepartment() != null) {
            final Student student = userToBeUpdated.getStudent();
//            student.setDepartment(departmentService.getDepartment(userProfileRequest.getDepartment()));
            userService.saveStudent(student);
        }
        final List<ImageModel> previousImages = userService.getImagesByUsername(authUsername);
        IntStream.range(0, previousImages.size()).forEach(index -> deleteImage(previousImages.get(index)));
        final ImageModel profileImage = ImageModel.builder().name(profilePhoto.getOriginalFilename()).type(profilePhoto.getContentType())
                .imageBinary(compressBytes(profilePhoto.getBytes())).imageType(ImageType.PROFILE).user(userToBeUpdated).build();
        final ImageModel coverImage = ImageModel.builder().name(coverPhoto.getOriginalFilename()).type(coverPhoto.getContentType())
                .imageBinary(compressBytes(coverPhoto.getBytes())).imageType(ImageType.COVER).user(userToBeUpdated).build();

        userService.saveImages(List.of(profileImage, coverImage));
        return mapperRegistry.getMapper(User.class, UserResponse.class).map(userService.updateUser(userToBeUpdated));
    }

    @Override
    public List<DropDown> getMemberList() {
        return userService.getAllStudents()
                .stream()
                .map(student -> mapperRegistry.getMapper(Student.class, DropDown.class).map(student))
                .collect(Collectors.toList());
    }

    @Override
    public List<DropDown> getCourseList() {
        return userService.getAllCourses()
                .stream()
                .map(course -> mapperRegistry.getMapper(Course.class, DropDown.class).map(course))
                .collect(Collectors.toList());
    }

    @Override
    public List<DropDown> getChapterList() {
        return userService.getAllChapters()
                .stream()
                .map(chapter -> mapperRegistry.getMapper(Chapter.class, DropDown.class).map(chapter))
                .collect(Collectors.toList());
    }

    @Override
    public UserProfileRequest fetchUserProfileFormData(@Nonnull final String authUsername) {
        final User user = userService.findUserByUsername(authUsername);
        final UserProfileRequest form = mapperRegistry.getMapper(User.class, UserProfileRequest.class).map(user);
        if (user.isStudent) {
            final Student student = userService.getStudentByUserId(user.getId());
//            form.setDepartment(mapperRegistry.getMapper(Department.class, DepartmentResponse.class).map(student.getDepartment()));
        }
        return form;
    }

    public List<ImageResponse> fetchUserImages(@Nonnull final String authUsername) {
        return userService.getImagesByUsername(authUsername).stream()
                .map(item -> ImageResponse.builder()
                        .name(item.getName())
                        .imageType(item.getImageType())
                        .type(item.getType())
                        .imageBinary(decompressBytes(item.getImageBinary()))
                        .build()).sorted(Comparator.comparing(ImageResponse::getImageType)).collect(Collectors.toList());
    }

    @Transactional
    public void deleteImage(final ImageModel imageModel) {
        userService.deleteImage(imageModel);
    }

    @Transactional
    @Override
    public UserResponse register(final RegisterRequest registerForm) {
        final User user = User.builder().build();
        user.setActive(true);
        user.setEmail(registerForm.getUsername());
        user.setUsername(registerForm.getUsername());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        user.setRegistrationDate(LocalDate.now());
        user.setRoles(Set.of(roleRepository.findByCode(SUPER_ADMIN_CODE).orElseThrow(IllegalStateException::new)));

        return mapperRegistry.getMapper(User.class, UserResponse.class).map(userService.saveUser(user));
    }

    @Transactional
    @Override
    public void connectWithUser(Long fromUserId, Long toUserId) throws IllegalAccessException {
        User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get()).orElseThrow(() -> new EntityNotFoundException(User.class));

        // user is not authorized
        if (!user.getId().equals(fromUserId)) {
            throw new IllegalAccessException();
        }
        User user2 = userRepository.findById(toUserId).orElseThrow(() -> new EntityNotFoundException(User.class));
        if (!user2.getId().equals(toUserId)) {
            throw new IllegalAccessException();
        }

        // user cannot like himself
        if(fromUserId == toUserId) {
            throw new RuntimeException();
        }

        // connection already exists
        final Long connectionExists = userService.checkIfConnectionExists(fromUserId, toUserId);
        if (connectionExists != null){
            deleteUserConnection(connectionExists);
        } else {
            userService.saveConnection(fromUserId, toUserId);
        }
    }

    @Transactional
    @Override
    public List<ConnectionResponse> getUserConnections() {
        User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get()).orElseThrow(() -> new EntityNotFoundException(User.class));
        final List<User> connections = userService.findConnections(user.getId());

        final List<ConnectionResponse> connectionResponses = new ArrayList<>();

        for (User userIter : connections){
            connectionResponses.add(mapperRegistry.getMapper(User.class, ConnectionResponse.class).map(userIter));
        }

        return connectionResponses;
    }

    @Transactional
    public void deleteUserConnection(final Long connectionId) {
        userService.deleteConnection(connectionId);
    }

    public List<MentorResponse> getMentors() {

        List<User> users = userService.getMentors();
        List<MentorResponse> mentors = new ArrayList<>();

        if (users == null) return mentors;

        for (User user : users) {
            mentors.add(mapperRegistry.getMapper(User.class, MentorResponse.class).map(user));
        }
        return mentors;
    }

    public List<MentorResponse> getMentorsByCourse(Long courseId) {

        List<User> users = userService.getMentorsByCourse(courseId);
        List<MentorResponse> mentors = new ArrayList<>();

        if (users == null) return mentors;

        for (User user : users) {
            mentors.add(mapperRegistry.getMapper(User.class, MentorResponse.class).map(user));
        }
        return mentors;
    }

    public List<CourseResponseShort> getCoursesByMentor(Long mentorId) {

        List<Course> courses = userService.getCoursesByMentor(mentorId);
        List<CourseResponseShort> courseResponses = new ArrayList<>();

        if (courses == null) return courseResponses;

        for (Course course : courses) {
            courseResponses.add(mapperRegistry.getMapper(Course.class, CourseResponseShort.class).map(course));
        }
        return courseResponses;
    }

}
