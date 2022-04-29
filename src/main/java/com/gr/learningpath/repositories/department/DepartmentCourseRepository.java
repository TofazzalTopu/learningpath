package com.gr.learningpath.repositories.department;

import com.gr.learningpath.domain.DepartmentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface DepartmentCourseRepository extends JpaRepository<DepartmentCourse, Long> {

    @Query("SELECT d FROM DepartmentCourse d WHERE d.department.id = ?1")
    Optional<List<DepartmentCourse>> findDepartmentCourseByDepartment(@Nonnull Long departmentId);


}
