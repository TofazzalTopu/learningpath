package com.gr.learningpath.util;

import com.gr.learningpath.domain.*;
import com.gr.learningpath.domain.course.Chapter;
import com.gr.learningpath.domain.course.CourseContent;
import com.gr.learningpath.domain.document.Document;
import com.gr.learningpath.domain.document.DocumentType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentUtil {

    public static List<Document> buildDocuments(MultipartFile[] documents, Chapter chapter, CourseContent courseContent, DocumentType type, Session session, Task task) throws IOException {
        if (chapter == null && courseContent == null && session == null) {
            throw new IllegalStateException();
        }

        final List<Document> resultDocs = new ArrayList<>();

        for (MultipartFile doc : documents) {
            if (chapter != null) {
                resultDocs.add(
                        Document.builder()
//                                .chapter(chapter)
                                .docName(doc.getName())
                                .docFileType(doc.getContentType())
                                .docContentCategory(type)
                                .data(doc.getBytes())
                                .build()
                );
            }
            if(courseContent !=null){
                resultDocs.add(
                        Document.builder()
//                                .courseContent(courseContent)
                                .docName(doc.getName())
                                .docFileType(doc.getContentType())
                                .docContentCategory(type)
                                .data(doc.getBytes())
                                .build()
                );
            }
            if (session != null) {
                resultDocs.add(
                        Document.builder()
//                                .session(session)
                                .docName(doc.getName())
                                .docFileType(doc.getContentType())
                                .docContentCategory(type)
                                .data(doc.getBytes())
                                .build()
                );
            }
            if (task != null) {
                resultDocs.add(
                        Document.builder()
//                                .task(task)
                                .docName(doc.getName())
                                .docFileType(doc.getContentType())
                                .docContentCategory(type)
                                .data(doc.getBytes())
                                .build()

                );
            }
        }
        return resultDocs;
    }

}
