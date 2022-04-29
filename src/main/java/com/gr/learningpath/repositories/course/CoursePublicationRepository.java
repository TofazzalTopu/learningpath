package com.gr.learningpath.repositories.course;

import com.gr.learningpath.domain.course.CoursePublication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursePublicationRepository extends JpaRepository<CoursePublication, Long> {

    CoursePublication findByCourseId(Long courseId);

    @Query("SELECT COUNT(c) FROM CoursePublication c where c.course.id = ?1")
    Long existsByCourseId(Long courseId);
}
