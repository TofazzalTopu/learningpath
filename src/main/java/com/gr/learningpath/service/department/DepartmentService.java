package com.gr.learningpath.service.department;

import com.gr.learningpath.domain.Department;
import com.gr.learningpath.domain.DepartmentCourse;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Service
public interface DepartmentService {

    List<Department> fetchDepartments();

    Optional<Department> findDepartment(@Nonnull Long id);

    List<DepartmentCourse> fetchAllDepartmentCourses();

    Department getDepartment(@Nonnull Long id);

    List<DepartmentCourse> fetchDepartmentCourses(Long departmentId);
}
