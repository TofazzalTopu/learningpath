package com.gr.learningpath.facade.department;

import com.gr.learningpath.api.response.department.DepartmentCoursesResponse;
import com.gr.learningpath.api.response.department.DepartmentResponse;
import com.gr.learningpath.api.response.department.DepartmentWithCoursesResponse;

import java.util.List;

public interface DepartmentFacade {

    List<DepartmentResponse> getDepartments();

    List<DepartmentCoursesResponse> getDepartmentCourses(Long departmentId);

    List<DepartmentWithCoursesResponse> getDepartmentWithCourses();

}
