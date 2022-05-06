package com.gr.learningpath.controller.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gr.learningpath.api.request.course.CourseRequest;
import com.gr.learningpath.api.request.course.PublishCourseRequest;
import com.gr.learningpath.api.response.course.CourseResponse;
import com.gr.learningpath.facade.course.CourseFacade;
import com.gr.learningpath.validator.course.CoursePublicationValidator;
import com.gr.learningpath.validator.course.CourseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Log4j2
public class CourseController {

    private final CourseFacade courseFacade;
    private final ObjectMapper objectMapper;
    private final CourseValidator validator;
    private final CoursePublicationValidator coursePublicationValidator;

    @PostMapping()
    public CourseResponse createCourse(@RequestParam("courseImage") final MultipartFile courseImage,
                                       @RequestPart("courseForm") final String courseRequestJson,
                                       final BindingResult result
    ) throws IOException {

        CourseRequest courseRequest;
        try {
            courseRequest = objectMapper.readValue(courseRequestJson, CourseRequest.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            //TODO handle this exception with @ControllerAdvice
            throw InvalidFormatException.from(null,
                    String.format("%s", e.getMessage()), courseRequestJson, byte[].class);
        }
        validator.validate(ObjectUtils.defaultIfNull(courseRequest, new CourseRequest()), result);

        return courseFacade.createOrUpdateCourse(courseRequest, courseImage);
    }

    @GetMapping()
    public List<CourseResponse> getCourseList() {
        return courseFacade.getCourses();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public CourseResponse getCourse(@PathVariable Long id) {
        return courseFacade.getCourse(id);
    }

    @DeleteMapping(path = "{courseId}")
    public void deleteCourse(@PathVariable("courseId") Long courseId) {
        courseFacade.deleteCourse(courseId);
    }

    @PostMapping("/publish")
    public CourseResponse publishCourse(@RequestBody final PublishCourseRequest publishRequestJson) {
        courseFacade.publishCourse(publishRequestJson);
        return courseFacade.getCourse(publishRequestJson.getCourseId());
    }

    @DeleteMapping("/unpublish/{courseId}")
    public CourseResponse unpublishCourse(@PathVariable final Long courseId) {
        courseFacade.unpublishCourse(courseId);
        return courseFacade.getCourse(courseId);
    }
}

