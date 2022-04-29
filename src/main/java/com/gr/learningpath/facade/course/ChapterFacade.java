package com.gr.learningpath.facade.course;

import com.gr.learningpath.api.request.course.ChapterRequest;
import com.gr.learningpath.api.response.course.ChapterResponse;
import com.gr.learningpath.api.response.course.CourseResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public interface ChapterFacade {

    CourseResponse createOrUpdateChapter(ChapterRequest chapterRequest, MultipartFile[] exercises, MultipartFile[] theories, MultipartFile[] solutions, MultipartFile[] relatedDocs) throws IOException;

    List<ChapterResponse> getAllChapterList(Long courseId);

    void deleteChapter(@Nonnull final Long chapterId);

}

