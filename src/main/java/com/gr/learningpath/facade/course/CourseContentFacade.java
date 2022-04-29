package com.gr.learningpath.facade.course;

import com.gr.learningpath.api.request.course.CourseContentRequest;
import com.gr.learningpath.api.response.course.CourseContentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CourseContentFacade {

    CourseContentResponse createOrUpdateCourseContent(CourseContentRequest courseContentForm,
                                                      MultipartFile[] exercises,
                                                      MultipartFile[] theories,
                                                      MultipartFile[] solutions,
                                                      MultipartFile[] relatedDocs) throws IOException;

    CourseContentResponse getCourseContent(Long courseId);

    void deleteCourseContent(Long courseContentId);
}
