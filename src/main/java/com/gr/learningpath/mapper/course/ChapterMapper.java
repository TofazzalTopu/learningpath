package com.gr.learningpath.mapper.course;

import com.gr.learningpath.api.response.course.ChapterResponse;
import com.gr.learningpath.api.response.course.DocumentResponse;
import com.gr.learningpath.domain.course.Chapter;
import com.gr.learningpath.domain.document.Document;
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
public class ChapterMapper {
    @NonNull
    private final MapperRegistry mapperRegistry;

    @PostConstruct
    private void registerMappers() {
        mapperRegistry.addMapper(Chapter.class, ChapterResponse.class, chapterToChapterResponse1());
        mapperRegistry.addMapper(Document.class, DocumentResponse.class, documentToDocumentResponse());
    }

    private Mapper<Chapter, ChapterResponse> chapterToChapterResponse1() {

        return chapter -> ChapterResponse
                .builder()
                .courseId(chapter.getCourse().getId())
                .chapterId(chapter.getId())
                .chapterTitle(chapter.getChapterTitle())
                .relativeUrl(chapter.getRelativeUrl())
                .effortPoints(chapter.getEffortPoints())
                .files(documentToDocumentResponses(chapter.getDocuments()))
                .build();
    }

    public List<DocumentResponse> documentToDocumentResponses(List<Document> documents) {
        List<DocumentResponse> documentResponses = new ArrayList<>();
        for (Document document1 : documents) {
            documentResponses.add(mapperRegistry.getMapper(Document.class, DocumentResponse.class).map(document1));
        }
        return documentResponses;
    }

    private Mapper<Document, DocumentResponse> documentToDocumentResponse() {
        return document -> DocumentResponse
                .builder()
                .docName(document.getDocName())
                .docFileType(document.getDocFileType())
                .docContentCategory(document.getDocContentCategory().name())
                .docBinary(document.getData())
                .build();
    }
}