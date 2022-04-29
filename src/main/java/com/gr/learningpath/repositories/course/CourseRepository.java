package com.gr.learningpath.repositories.course;

import com.gr.learningpath.domain.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    @Query("SELECT c FROM Course c WHERE c.title = ?1 AND c.user.id = ?2")
    Optional<Course> findByTitleAndUserId(String courseTitle, Long userId);

    @Query("SELECT c FROM Course c WHERE c.title = ?1 AND NOT c.id = ?2 AND c.user.id = ?3")
    Optional<Course> findByTitleForUpdateCourse(String courseTitle, Long id, Long userId);

    Optional<List<Course>> findByUserId(Long userId);

    @Query("SELECT c FROM Course c WHERE c.id = ?1 AND c.user.id = ?2")
    Optional<Course> findByIdAndUser(Long Id, Long userId);

    @Query("SELECT c FROM Course c " +
            "JOIN User user " +
            "ON user.id = c.user.id " +
            "WHERE user.isMentor = true AND user.id = ?1")
    Optional<List<Course>> fetchCoursesByMentor(Long mentorId);
}

