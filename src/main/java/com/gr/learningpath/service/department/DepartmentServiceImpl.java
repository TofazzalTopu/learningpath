package com.gr.learningpath.service.department;

import com.gr.learningpath.domain.Department;
import com.gr.learningpath.domain.DepartmentCourse;
import com.gr.learningpath.exceptions.EntityNotFoundException;
import com.gr.learningpath.repositories.department.DepartmentCourseRepository;
import com.gr.learningpath.repositories.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;
    private final DepartmentCourseRepository departmentCourseRepository;

    @Override
    public List<Department> fetchDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> findDepartment(@Nonnull Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department getDepartment(@Nonnull Long id) {
        return findDepartment(id).orElseThrow(() -> new EntityNotFoundException(Department.class));
    }

    @Override
    public List<DepartmentCourse> fetchAllDepartmentCourses(){
        List<DepartmentCourse> departmentCourseByDepartment = departmentCourseRepository.findAll();
        return departmentCourseByDepartment.isEmpty() ? null : departmentCourseByDepartment;
    }

    @Override
    public List<DepartmentCourse> fetchDepartmentCourses(@Nonnull Long departmentId) {
        Optional<List<DepartmentCourse>> departmentCourseByDepartment = departmentCourseRepository.findDepartmentCourseByDepartment(departmentId);
        return departmentCourseByDepartment.isEmpty() ? null : departmentCourseByDepartment.get();
    }

}
