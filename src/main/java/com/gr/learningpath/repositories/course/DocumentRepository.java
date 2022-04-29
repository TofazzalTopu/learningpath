package com.gr.learningpath.repositories.course;

import com.gr.learningpath.domain.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<List<Document>> findAllByChapterId(@Nonnull final Long chapterId);
}
