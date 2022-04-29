package com.gr.learningpath.mapper.department;

import com.gr.learningpath.api.response.department.DepartmentCoursesResponse;
import com.gr.learningpath.api.response.department.DepartmentResponse;
import com.gr.learningpath.api.response.department.DepartmentWithCoursesResponse;
import com.gr.learningpath.domain.Department;
import com.gr.learningpath.domain.DepartmentCourse;
import com.gr.learningpath.mapper.Mapper;
import com.gr.learningpath.mapper.MapperRegistry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class DepartmentMapper {
    @NonNull
    private final MapperRegistry mapperRegistry;

    @PostConstruct
    private void registerMappers() {
        mapperRegistry.addMapper(Department.class, DepartmentResponse.class, departmentToDepartmentResponseMapper());
        mapperRegistry.addMapper(DepartmentCourse.class, DepartmentCoursesResponse.class, departmentCourseToDepartmentCoursesResponseMapper());
        mapperRegistry.addMapper(Department.class, DepartmentWithCoursesResponse.class, departmentToDepartmentWithCoursesResponseMapper());
    }

    private Mapper<Department, DepartmentResponse> departmentToDepartmentResponseMapper() {
        return department -> DepartmentResponse.builder()
                .id(department.getId())
                .title(department.getName())
                .build();
    }

    private Mapper<Department, DepartmentWithCoursesResponse> departmentToDepartmentWithCoursesResponseMapper() {
        return department -> DepartmentWithCoursesResponse.builder()
                .id(department.getId())
                .title(department.getName())
                .departmentCoursesResponseList(getDepartmentCourseResponse(department))
                .build();
    }

    private List<DepartmentCoursesResponse> getDepartmentCourseResponse(Department department){
        List<DepartmentCoursesResponse> departmentCoursesResponses = new ArrayList<>();

        List<DepartmentCourse> departmentCourses = department.getDepartmentCourses();
        for (DepartmentCourse dep : departmentCourses){
            departmentCoursesResponses
                    .add(mapperRegistry.getMapper(DepartmentCourse.class, DepartmentCoursesResponse.class).map(dep));
        }
        return departmentCoursesResponses;
    }

    private Mapper<DepartmentCourse, DepartmentCoursesResponse> departmentCourseToDepartmentCoursesResponseMapper(){
        return departmentCourses -> DepartmentCoursesResponse
                .builder()
                .courseId(departmentCourses.getId())
                .name(departmentCourses.getName())
                .semester(departmentCourses.getSemester())
                .build();
    }
}