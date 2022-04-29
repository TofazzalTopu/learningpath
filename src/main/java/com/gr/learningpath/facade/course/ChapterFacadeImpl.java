package com.gr.learningpath.facade.course;

import com.gr.learningpath.api.request.course.ChapterRequest;
import com.gr.learningpath.api.response.course.ChapterResponse;
import com.gr.learningpath.api.response.course.CourseResponse;
import com.gr.learningpath.domain.User;
import com.gr.learningpath.domain.course.Chapter;
import com.gr.learningpath.domain.course.Course;
import com.gr.learningpath.domain.document.Document;
import com.gr.learningpath.domain.document.DocumentType;
import com.gr.learningpath.exceptions.EntityAlreadyExistsException;
import com.gr.learningpath.exceptions.EntityNotFoundException;
import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.repositories.UserRepository;
import com.gr.learningpath.security.SecurityUtils;
import com.gr.learningpath.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChapterFacadeImpl implements ChapterFacade {
    private final CourseService courseService;
    private final MapperRegistry mapperRegistry;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public CourseResponse createOrUpdateChapter(final ChapterRequest chapterRequest, final MultipartFile[] exercises, final MultipartFile[] theories, final MultipartFile[] solutions, final MultipartFile[] relatedDocuments) throws IOException {
        User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get()).orElseThrow(() -> new EntityNotFoundException(User.class));
        Course course = courseService.findCourse(chapterRequest.getCourseId(), user.getId()).orElseThrow(() -> new EntityNotFoundException(Course.class));

        final Long chapterFormId = chapterRequest.getId();
        final boolean updateState = (chapterFormId!=null);

        if (!updateState && course.getChapterList().stream()
                .anyMatch(s -> s.getChapterTitle().equals(chapterRequest.getChapterTitle()))) {
            throw new EntityAlreadyExistsException(Chapter.class);
        }

        final Chapter chapter;
        if (!updateState) {

            chapter = Chapter.builder()
                    .id(chapterFormId)
                    .chapterTitle(chapterRequest.getChapterTitle())
                    .course(courseService.getCourse(chapterRequest.getCourseId(), user.getId()))
                    .effortPoints(chapterRequest.getEffortPoints())
                    .relativeUrl(chapterRequest.getRelativeUrl())
                    .build();

        } else {

            chapter = course.getChapterList()
                    .stream()
                    .filter(s -> s.getId().equals(chapterFormId))
                    .findFirst()
                    .orElseThrow( () -> new EntityNotFoundException(Chapter.class));
        }

        List<Document> documentsRequest = getRequestDocuments(exercises, theories, solutions, relatedDocuments, chapter);

        if (!updateState) {
            chapter.setDocuments(documentsRequest);
            course.getChapterList().add(chapter);
        } else {
            chapter.setChapterTitle(chapterRequest.getChapterTitle());
            chapter.setRelativeUrl(chapterRequest.getRelativeUrl());
            chapter.setEffortPoints(chapterRequest.getEffortPoints());
            chapter.getDocuments().clear();
            chapter.getDocuments().addAll(documentsRequest);
        }
        courseService.updateChapter(course);

        return mapperRegistry.getMapper(Course.class, CourseResponse.class).map(course);

    }

    private List<Document> getRequestDocuments(MultipartFile[] exercises, MultipartFile[] theories, MultipartFile[] solutions, MultipartFile[] relatedDocuments, Chapter chapter) throws IOException {
        final List<Document> exerciseDocs = buildDocuments(exercises, chapter, DocumentType.EXERCISE);
        final List<Document> theoriesDocs = buildDocuments(theories, chapter, DocumentType.THEORY);
        final List<Document> solutionDocs = buildDocuments(solutions, chapter, DocumentType.SOLUTION);
        final List<Document> relatedDocs = buildDocuments(relatedDocuments, chapter, DocumentType.RELATED_DOCS);

        List<Document> allDocs = Stream.of(exerciseDocs, theoriesDocs, solutionDocs, relatedDocs)
                .flatMap(Collection::stream)
                .filter(s -> !s.getDocName().isBlank())
                .collect(Collectors.toList());
        return allDocs;
    }

    public List<ChapterResponse> getAllChapterList(Long courseId) {
        List<Chapter> allChapters = courseService.getAllChapter(courseId);
        List<ChapterResponse> chapterResponses = new ArrayList<>();
        for (Chapter chapter: allChapters){
            chapterResponses.add(mapperRegistry.getMapper(Chapter.class, ChapterResponse.class).map(chapter));
        }
        return chapterResponses;
    }

    @Transactional
    public void deleteChapter(@Nonnull final Long chapterId) {
        User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get()).orElseThrow(() -> new EntityNotFoundException(User.class));
        courseService.deleteChapter(chapterId, user.getId());
    }


    private List<Document> buildDocuments(MultipartFile[] documents, Chapter chapter, DocumentType type) throws IOException {
        final List<Document> resultDocs = new ArrayList<>();
        for (MultipartFile doc : documents) {
            resultDocs.add(
                    Document.builder()
                            .chapter(chapter)
                            .docName(doc.getOriginalFilename())
                            .docFileType(doc.getContentType())
                            .docContentCategory(type)
                            .data(doc.getBytes())
                            .build()
            );
        }
        return resultDocs;
    }
}
