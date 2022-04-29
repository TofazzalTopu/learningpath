package com.gr.learningpath.repositories.course;

import com.gr.learningpath.domain.course.CourseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository

public interface CourseContentRepository extends JpaRepository<CourseContent, Long> {

    Optional<CourseContent> findCourseContentById (@Nonnull final Long courseId);

    Optional<CourseContent> findCourseContentsByCourseId (@Nonnull final Long courseId);

}
