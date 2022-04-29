package com.gr.learningpath.facade.course;

import com.gr.learningpath.api.request.course.CourseRequest;
import com.gr.learningpath.api.request.course.PublishCourseRequest;
import com.gr.learningpath.api.response.course.CourseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseFacade {

    CourseResponse createOrUpdateCourse(final CourseRequest courseRequest, final MultipartFile courseImage) throws IOException;

    List<CourseResponse> getCourses();

    CourseResponse getCourse(final Long id);

    void deleteCourse(Long courseId);

    void publishCourse(final PublishCourseRequest publishCourseRequest);

    void unpublishCourse(Long courseId);
}
