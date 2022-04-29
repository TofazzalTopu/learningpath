package com.gr.learningpath.controller.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gr.learningpath.api.request.course.CourseContentRequest;
import com.gr.learningpath.api.response.course.CourseContentResponse;
import com.gr.learningpath.facade.course.CourseContentFacade;
import com.gr.learningpath.validator.course.CourseContentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/course/course-content")
@RequiredArgsConstructor
@Log4j2
public class CourseContentController {

    private final CourseContentFacade courseContentFacade;
    private final ObjectMapper objectMapper;
    private final CourseContentValidator validator;

    @PostMapping()
    public CourseContentResponse createCourseContent(@RequestParam("exercises") final MultipartFile[] exercises,
                                                     @RequestParam("theories") final MultipartFile[] theories,
                                                     @RequestParam("solutions") final MultipartFile[] solutions,
                                                     @RequestParam("relatedDocs") final MultipartFile[] relatedDocs,
                                                     @RequestPart("courseContentForm") final String courseContentRequestJson,
                                                     final BindingResult result
    ) throws IOException {

        CourseContentRequest courseContentRequest;
        try {
            courseContentRequest = objectMapper.readValue(courseContentRequestJson, CourseContentRequest.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            //TODO handle this exception with @ControllerAdvice
            throw InvalidFormatException.from(null,
                    String.format("%s", e.getMessage()), courseContentRequestJson, byte[].class);
        }
        validator.validate(ObjectUtils.defaultIfNull(courseContentRequest, new CourseContentRequest()), result);

        return courseContentFacade.createOrUpdateCourseContent(courseContentRequest, exercises, theories, solutions, relatedDocs);

    }

    @GetMapping(value = "/{courseId}", produces = APPLICATION_JSON_VALUE)
    public CourseContentResponse getCourseContent(@PathVariable Long courseId){
        return courseContentFacade.getCourseContent(courseId);
    }

    @DeleteMapping(value = "/{courseContentId}", produces = APPLICATION_JSON_VALUE)
    public void deleteCourseContent(@PathVariable Long courseContentId) { courseContentFacade.deleteCourseContent(courseContentId);  }

}

