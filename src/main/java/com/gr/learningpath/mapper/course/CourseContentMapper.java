package com.gr.learningpath.mapper.course;

import com.gr.learningpath.api.response.course.CourseContentDocumentResponse;
import com.gr.learningpath.api.response.course.CourseContentResponse;
import com.gr.learningpath.domain.course.CourseContent;
import com.gr.learningpath.domain.course.CourseContentDocument;
import com.gr.learningpath.mapper.Mapper;
import com.gr.learningpath.mapper.MapperRegistry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseContentMapper {

    @NonNull
    private final MapperRegistry mapperRegistry;

    @PostConstruct
    private void registerMappers() {
        mapperRegistry.addMapper(CourseContent.class, CourseContentResponse.class, courseContentToCourseContentResponse());
        mapperRegistry.addMapper(CourseContentDocument.class, CourseContentDocumentResponse.class, courseContentDocumentToCourseContentDocumentResponse());
    }

    private Mapper<CourseContent, CourseContentResponse> courseContentToCourseContentResponse() {

        return courseContent -> CourseContentResponse
                .builder()
                .Id(courseContent.getId())
                .relativeUrl(courseContent.getRelativeUrl())
                .files(getDocuments(courseContent))
                .build();
    }

    public List<CourseContentDocumentResponse> getDocuments(CourseContent courseContent) {
        List<CourseContentDocumentResponse> documentResponses = new ArrayList<>();
        for (CourseContentDocument doc : courseContent.getDocuments()) {
            documentResponses.add(mapperRegistry.getMapper(CourseContentDocument.class, CourseContentDocumentResponse.class).map(doc));
        }
        return documentResponses;
    }

    private Mapper<CourseContentDocument, CourseContentDocumentResponse> courseContentDocumentToCourseContentDocumentResponse() {

        return courseContentDocument -> CourseContentDocumentResponse
                .builder()
                .docName(courseContentDocument.getDocName())
                .docFileType(courseContentDocument.getDocFileType())
                .docContentCategory(courseContentDocument.getDocContentCategory())
                .data(courseContentDocument.getData())
                .build();
    }

}
