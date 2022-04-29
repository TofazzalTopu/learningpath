package com.gr.learningpath.facade.course;

import com.gr.learningpath.api.request.course.CourseRequest;
import com.gr.learningpath.api.request.course.PublishCourseRequest;
import com.gr.learningpath.api.response.course.CourseResponse;
import com.gr.learningpath.domain.User;
import com.gr.learningpath.domain.course.Course;
import com.gr.learningpath.exceptions.EntityNotFoundException;
import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.repositories.UserRepository;
import com.gr.learningpath.security.SecurityUtils;
import com.gr.learningpath.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseFacadeImpl implements CourseFacade {
    private final MapperRegistry mapperRegistry;
    private final CourseService courseService;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public CourseResponse createOrUpdateCourse(final CourseRequest courseRequest, final MultipartFile courseImage) throws IOException {

        courseRequest.setCourseImageName(courseImage.getOriginalFilename());
        courseRequest.setImageType(courseImage.getContentType());
        courseRequest.setImageBinary(courseImage.getBytes());

        final Course course = mapperRegistry.getMapper(CourseRequest.class, Course.class)
                .map(courseRequest);

        User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get()).orElseThrow(() -> new EntityNotFoundException(User.class));
        course.setUser(user);

        if (course.getId() == null) {
            Course source = courseService.saveCourse(course);
            return mapperRegistry.getMapper(Course.class, CourseResponse.class).map(source);

        } else {
            Course courseToBeUpdated = courseService.findCourse(course.getId(), user.getId()).orElseThrow(() -> new EntityNotFoundException(Course.class));
            courseToBeUpdated.setTitle(course.getTitle());
            courseToBeUpdated.setDepartmentCourse(course.getDepartmentCourse());
            courseToBeUpdated.setImageName(course.getImageName());
            courseToBeUpdated.setImageType(course.getImageType());
            courseToBeUpdated.setImageBinary(course.getImageBinary());
            courseToBeUpdated.setSemester(course.getSemester());
            courseToBeUpdated.setDescription(course.getDescription());
            courseToBeUpdated.setDepartment(course.getDepartment());

            courseService.updateCourse(courseToBeUpdated);
            return mapperRegistry.getMapper(Course.class, CourseResponse.class).map(courseToBeUpdated);
        }
    }


    public List<CourseResponse> getCourses() {
        final User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get()).orElseThrow(() -> new EntityNotFoundException(User.class));
        List<Course> allCourses = courseService.getAllCourses(user.getId()).orElse(null);

        if (allCourses == null) return null;

        return allCourses
                .stream()
                .map(course -> mapperRegistry.getMapper(Course.class, CourseResponse.class).map(course))
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponse getCourse(final Long id) {
        final User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get()).orElseThrow(() -> new EntityNotFoundException(User.class));
        Course course = courseService.findCourse(id, user.getId()).orElseThrow(() -> new EntityNotFoundException(Course.class));
        return mapperRegistry.getMapper(Course.class, CourseResponse.class).map(course);
    }

    @Transactional
    @Override
    public void deleteCourse(final Long id) {
        courseService.deleteCourse(id);
    }

    @Transactional
    @Override
    public void publishCourse(final PublishCourseRequest publishCourseRequest){
        courseService.publishCourse(publishCourseRequest.getCourseId(), publishCourseRequest.getIsPrivate());
    }

    @Transactional
    @Override
    public void unpublishCourse(final Long courseId){
        final User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get())
                .orElseThrow(() -> new EntityNotFoundException(User.class));
        courseService.findCourse(courseId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException(Course.class));

        courseService.deletePublication(courseId);
    }


}

