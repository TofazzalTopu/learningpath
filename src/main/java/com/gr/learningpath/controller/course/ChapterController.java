package com.gr.learningpath.controller.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.learningpath.api.request.course.ChapterRequest;
import com.gr.learningpath.api.response.course.ChapterResponse;
import com.gr.learningpath.api.response.course.CourseResponse;
import com.gr.learningpath.facade.course.ChapterFacade;
import com.gr.learningpath.validator.course.ChapterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/course/chapters")
@RequiredArgsConstructor
@Log4j2
public class ChapterController {

    private final ChapterFacade chapterFacade;
    private final ObjectMapper objectMapper;
    private final ChapterValidator validator;

    @PostMapping()
    public CourseResponse createOrUpdateChapter(@RequestParam("exercises") final MultipartFile[] exercises,
                                        @RequestParam("theories") final MultipartFile[] theories,
                                        @RequestParam("solutions") final MultipartFile[] solutions,
                                        @RequestParam("relatedDocs") final MultipartFile[] relatedDocs,
                                        @RequestPart("chapterForm") final String chapterFormJson,
                                        final BindingResult result
    ) throws IOException {

        ChapterRequest chapterRequest = null;
        try {
            chapterRequest = objectMapper.readValue(chapterFormJson, ChapterRequest.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        validator.validate(ObjectUtils.defaultIfNull(chapterRequest, new ChapterRequest()), result);
        return chapterFacade.createOrUpdateChapter(chapterRequest, exercises, theories, solutions, relatedDocs);
    }

    @GetMapping(value = "/{courseId}", produces = APPLICATION_JSON_VALUE)
    public List<ChapterResponse> getChapters(@PathVariable Long courseId) {
        return chapterFacade.getAllChapterList(courseId);
    }

    @DeleteMapping(value = "/{chapterId}", produces = APPLICATION_JSON_VALUE)
    public void deleteChapter(@PathVariable Long chapterId) {
        chapterFacade.deleteChapter(chapterId);
    }

}
