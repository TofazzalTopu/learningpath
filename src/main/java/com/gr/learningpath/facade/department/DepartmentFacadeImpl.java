package com.gr.learningpath.facade.department;

import com.gr.learningpath.api.response.department.DepartmentCoursesResponse;
import com.gr.learningpath.api.response.department.DepartmentResponse;
import com.gr.learningpath.api.response.department.DepartmentWithCoursesResponse;
import com.gr.learningpath.domain.Department;
import com.gr.learningpath.domain.DepartmentCourse;
import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentFacadeImpl implements DepartmentFacade {

    private final DepartmentService departmentService;
    private final MapperRegistry mapperRegistry;

    public List<DepartmentResponse> getDepartments() {
        return departmentService.fetchDepartments()
                .stream()
                .map(department -> mapperRegistry.getMapper(Department.class, DepartmentResponse.class).map(department))
                .collect(Collectors.toList());
    }

    public List<DepartmentCoursesResponse> getDepartmentCourses() {
        List<DepartmentCourse> departmentCourses = departmentService.fetchAllDepartmentCourses();
        List<DepartmentCoursesResponse> departmentCoursesResponses = new ArrayList<>();
        for (DepartmentCourse course: departmentCourses){
            departmentCoursesResponses.add(mapperRegistry.getMapper(DepartmentCourse.class, DepartmentCoursesResponse.class).map(course));
        }
        return departmentCoursesResponses;
    }

    public List<DepartmentWithCoursesResponse> getDepartmentWithCourses() {
        return departmentService.fetchDepartments()
                .stream()
                .map(department -> mapperRegistry.getMapper(Department.class, DepartmentWithCoursesResponse.class).map(department))
                .collect(Collectors.toList());
    }



    public List<DepartmentCoursesResponse> getDepartmentCourses(Long departmentId) {

        List<DepartmentCourse> departmentCourses = departmentService.fetchDepartmentCourses(departmentId);
        if (departmentCourses == null) return null;
        List<DepartmentCoursesResponse> departmentCoursesResponses = new ArrayList<>();
        for (DepartmentCourse course: departmentCourses){
            departmentCoursesResponses.add(mapperRegistry.getMapper(DepartmentCourse.class, DepartmentCoursesResponse.class).map(course));
        }
        return departmentCoursesResponses;
    }

}
