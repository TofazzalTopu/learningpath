package com.gr.learningpath.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "student")
public class Student extends BaseEntity {

    private static final long serialVersionUID = -2044277688315933737L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "fk_student_department_id"))
    private Department department;

    private Integer semester;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_student_user_id"))
    private User user;


}
