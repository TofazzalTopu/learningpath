package com.gr.learningpath.mapper.course;

import com.gr.learningpath.api.request.course.CourseRequest;
import com.gr.learningpath.api.response.course.*;
import com.gr.learningpath.domain.DepartmentCourse;
import com.gr.learningpath.domain.course.Chapter;
import com.gr.learningpath.domain.course.Course;
import com.gr.learningpath.domain.course.CourseContent;
import com.gr.learningpath.exceptions.EntityNotFoundException;
import com.gr.learningpath.mapper.Mapper;
import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.repositories.department.DepartmentCourseRepository;
import com.gr.learningpath.service.course.CourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class CourseMapper {
    @NonNull
    private final MapperRegistry mapperRegistry;
    @NonNull
    private final CourseService courseService;

    @Nonnull
    private final DepartmentCourseRepository departmentCourseRepository;

    @PostConstruct
    private void registerMappers() {
        mapperRegistry.addMapper(CourseRequest.class, Course.class, courseRequestToCourseMapper());
        mapperRegistry.addMapper(Course.class, CourseResponse.class, courseToCourseResponse());
        mapperRegistry.addMapper(Course.class, CourseResponseShort.class, courseToCourseResponseShort());
    }

    private Mapper<CourseRequest, Course> courseRequestToCourseMapper() {
        return courseRequest -> Course
                .builder()
                .id(courseRequest.getId() != null ? courseRequest.getId() : null)
                .title(courseRequest.getTitle())
                .departmentCourse(courseRequest.getRelatedCourse() != null ?
                        departmentCourseRepository.findById(courseRequest.getRelatedCourse()).orElseThrow(() -> new EntityNotFoundException(DepartmentCourse.class)) : null)
                .description(courseRequest.getDescription() != null ? courseRequest.getDescription() : null)
                .imageType(courseRequest.getImageType() != null ? courseRequest.getImageType() : null)
                .imageName(courseRequest.getCourseImageName() != null ? courseRequest.getCourseImageName() : null)
                .imageBinary(courseRequest.getImageBinary() != null ? courseRequest.getImageBinary() : null)
                .semester(courseRequest.getSemester() != null ? courseRequest.getSemester() : null)
                .department(courseRequest.getDepartmentId() != null ? courseService.getDepartment(courseRequest.getDepartmentId()) : null)
                .build();
    }

    private Mapper<Course, CourseResponse> courseToCourseResponse() {
        return course -> CourseResponse
                .builder()
                .id(course.getId())
                .department(course.getDepartment() != null ? course.getDepartment().getName() : null)
                .title(course.getTitle())
                .relatedCourse(course.getDepartmentCourse() != null ? course.getDepartmentCourse().getName() : null)
                .semester(course.getSemester())
                .description(course.getDescription())
                .courseContent(course.getCourseContent() != null ? mapperRegistry.getMapper(CourseContent.class, CourseContentResponse.class).map(course.getCourseContent()) : null)
                .image(new CourseImageResponse(course.getImageName(), course.getImageType(), course.getImageBinary()))
                .chapters(course.getChapterList()
                        .stream()
                        .map(s -> mapperRegistry.getMapper(Chapter.class, ChapterResponse.class).map(s))
                        .collect(Collectors.toList()))
                .coursePublication(new CoursePublicationResponse(course.getCoursePublication() != null ? course.getCoursePublication().isPrivate() : null))
                .build();
    }

    private Mapper<Course, CourseResponseShort> courseToCourseResponseShort() {
        return course -> CourseResponseShort
                .builder()
                .id(course.getId())
                .department(course.getDepartment() != null ? course.getDepartment().getName() : null)
                .title(course.getTitle())
                .build();
    }

}

