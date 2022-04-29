package com.gr.learningpath.domain;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "department", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "uk_departments_name")})
public class Department extends BaseEntity implements Versionable {
    private String name;
    @Version
    private Long version;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    List<DepartmentCourse> departmentCourses = new ArrayList<>();

}
