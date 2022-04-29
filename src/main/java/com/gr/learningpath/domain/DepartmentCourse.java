package com.gr.learningpath.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department_courses")
public class DepartmentCourse extends BaseEntity{

    private int semester;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_department_course_department_id"))
    private Department department;


}
