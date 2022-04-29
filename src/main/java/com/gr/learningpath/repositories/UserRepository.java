package com.gr.learningpath.repositories;

import com.gr.learningpath.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByUsername(@Nonnull final String username);

    Optional<User> findByUsername(@Nonnull final String username);

    boolean existsByUsername(@Nonnull final String username);

    @Query("SELECT m FROM User m where m.isMentor = true")
    Optional<List<User>> fetchMentors();

    @Query("SELECT user FROM User user " +
            "JOIN Course c ON c.user.id = user.id " +
            "JOIN CoursePublication cp on c.id = cp.course.id " +
            "WHERE c.departmentCourse.id = ?1")
    Optional<List<User>> fetchMentorsByCourse(Long courseId);

}
