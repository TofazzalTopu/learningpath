package com.gr.learningpath.repositories.course;

import com.gr.learningpath.domain.course.Chapter;
import com.gr.learningpath.domain.course.CourseChapterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    Optional<Chapter> findByCourseIdAndId(@Nonnull final Long courseId, @Nonnull final Long id);

    List<Chapter> findByCourseId(@Nonnull final Long courseId);

//    @Query("SELECT new com.gr.learningpath.domain.course.CourseChapterDTO(c.id, c.chapterTitle, c.relativeUrl, c.effortPoints, d.docContentCategory, d.docName, d.docFileType, d.data)" +
//            " FROM Chapter c inner JOIN c.documents d WHERE c.course.id = ?1")
    List<Chapter> findAllByCourseId(@Nonnull final Long courseId);

    @Query("SELECT new com.gr.learningpath.domain.course.CourseChapterDTO(c.id,c.chapterTitle, c.relativeUrl, c.effortPoints, d.docContentCategory, d.docName, d.docFileType, d.data)" +
            " FROM Chapter c LEFT JOIN c.documents d WHERE c.course.id = ?1 AND c.chapterTitle = ?2")
    List<CourseChapterDTO> findByCourseIdAndChapterTitle(@Nonnull final Long courseId, @Nonnull final String chapterTitle);


    @Query("SELECT c FROM Chapter c WHERE c.chapterTitle = ?1 AND c.course.id = ?2")
    Optional<List<Chapter>> findByChapterTitleAndCourse(@Nonnull final String chapterTitle, @Nonnull final Long courseId);

}

