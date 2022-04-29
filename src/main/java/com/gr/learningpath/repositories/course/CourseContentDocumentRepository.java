package com.gr.learningpath.repositories.course;

import com.gr.learningpath.domain.course.CourseContentDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CourseContentDocumentRepository extends JpaRepository<CourseContentDocument, Long> {
    Iterable<? extends CourseContentDocument> findByCourseContentId(Long id);
}
