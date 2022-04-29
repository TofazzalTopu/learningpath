package com.gr.learningpath.facade.course;

import com.gr.learningpath.api.request.course.CourseContentRequest;
import com.gr.learningpath.api.response.course.CourseContentResponse;
import com.gr.learningpath.domain.User;
import com.gr.learningpath.domain.course.Course;
import com.gr.learningpath.domain.course.CourseContent;
import com.gr.learningpath.domain.course.CourseContentDocument;
import com.gr.learningpath.domain.document.DocumentType;
import com.gr.learningpath.exceptions.EntityAlreadyExistsException;
import com.gr.learningpath.exceptions.EntityNotFoundException;
import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.repositories.UserRepository;
import com.gr.learningpath.repositories.course.CourseContentDocumentRepository;
import com.gr.learningpath.repositories.course.CourseContentRepository;
import com.gr.learningpath.security.SecurityUtils;
import com.gr.learningpath.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseContentFacadeImp implements CourseContentFacade {
    private final CourseService courseService;
    private final MapperRegistry mapperRegistry;
    private final UserRepository userRepository;
    private final CourseContentRepository courseContentRepository;
    private final CourseContentDocumentRepository courseContentDocumentRepository;
    @Transactional
    @Override
    public CourseContentResponse createOrUpdateCourseContent(final CourseContentRequest courseContentRequest,
                                                             final MultipartFile[] exercises,
                                                             final MultipartFile[] theories,
                                                             final MultipartFile[] solutions,
                                                             final MultipartFile[] relatedDocuments) throws IOException {

        User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get()).orElseThrow(() -> new EntityNotFoundException(User.class));
        Course course = courseService.findCourse(courseContentRequest.getCourseId(), user.getId()).orElseThrow(() -> new EntityNotFoundException(Course.class));

        final Long courseContentId = courseContentRequest.getId();
        final boolean updateState = (courseContentId != null);

        final CourseContent courseContent;

        // check if courseContent id already exists
        final Optional<CourseContent> courseContentById = courseContentRepository.findCourseContentById(courseContentId);
        final Optional<CourseContent> courseContentByCourseId = courseContentRepository.findCourseContentsByCourseId(courseContentRequest.getCourseId());

        if (!updateState) {

            if (!courseContentByCourseId.isEmpty())
                throw new EntityAlreadyExistsException(CourseContent.class);

            courseContent = CourseContent.builder()
                    .id(courseContentId)
                    .course(courseService.getCourse(courseContentRequest.getCourseId(), user.getId()))
                    .relativeUrl(courseContentRequest.getRelativeUrl())
                    .build();

        } else {

            if (courseContentById.isEmpty())
                throw new EntityNotFoundException(CourseContent.class);

            if (!courseContentById.isEmpty() && courseContentById.get().getCourse().getId() != course.getId())
                throw new EntityNotFoundException(CourseContent.class);

            courseContent = course.getCourseContent();

        }

        List<CourseContentDocument> documentsRequest = getRequestDocuments(exercises, theories, solutions, relatedDocuments, courseContent);

        if (!updateState) {
            courseContent.setDocuments(documentsRequest);
            course.setCourseContent(courseContent);
        } else {
            courseContentDocumentRepository.deleteAll(courseContentDocumentRepository.findByCourseContentId(courseContent.getId()));
            courseContent.setCourse(course);
            courseContent.setRelativeUrl(courseContentRequest.getRelativeUrl());
            courseContent.getDocuments().clear();
            courseContent.getDocuments().addAll(documentsRequest);
            course.setCourseContent(courseContent);

        }
        courseService.updateChapter(course);

        return mapperRegistry.getMapper(CourseContent.class, CourseContentResponse.class).map(courseContent);

    }

    private List<CourseContentDocument> getRequestDocuments(MultipartFile[] exercises,
                                                            MultipartFile[] theories,
                                                            MultipartFile[] solutions,
                                                            MultipartFile[] relatedDocuments,
                                                            CourseContent courseContent) throws IOException {

        final List<CourseContentDocument> exerciseDocs = buildDocuments(exercises, courseContent, DocumentType.EXERCISE);
        final List<CourseContentDocument> theoriesDocs = buildDocuments(theories, courseContent, DocumentType.THEORY);
        final List<CourseContentDocument> solutionDocs = buildDocuments(solutions, courseContent, DocumentType.SOLUTION);
        final List<CourseContentDocument> relatedDocs = buildDocuments(relatedDocuments, courseContent, DocumentType.RELATED_DOCS);

        List<CourseContentDocument> allDocs = Stream.of(exerciseDocs, theoriesDocs, solutionDocs, relatedDocs)
                .flatMap(Collection::stream)
                .filter(s -> !s.getDocName().isBlank())
                .collect(Collectors.toList());
        return allDocs;
    }

    private List<CourseContentDocument> buildDocuments(MultipartFile[] documents, CourseContent courseContent, DocumentType type) throws IOException {
        final List<CourseContentDocument> resultDocs = new ArrayList<>();
        for (MultipartFile doc : documents) {
            resultDocs.add(
                    CourseContentDocument.builder()
                            .courseContent(courseContent)
                            .docName(doc.getOriginalFilename())
                            .docFileType(doc.getContentType())
                            .docContentCategory(type)
                            .data(doc.getBytes())
                            .build()
            );
        }
        return resultDocs;
    }

    public CourseContentResponse getCourseContent(Long courseId) {
        CourseContent courseContent = courseService.findCourseContent(courseId).orElseThrow(() -> new EntityNotFoundException(CourseContent.class));
        return mapperRegistry.getMapper(CourseContent.class, CourseContentResponse.class).map(courseContent);
    }

    @Transactional
    public void deleteCourseContent(Long courseContentId) {
        User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get()).orElseThrow(() -> new EntityNotFoundException(User.class));
        courseService.findCourseContent(courseContentId).orElseThrow(() -> new EntityNotFoundException(CourseContent.class));
        courseService.deleteCourseContent(courseContentId, user.getId());
    }

}
