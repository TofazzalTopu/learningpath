package com.gr.learningpath.domain.course;

import com.gr.learningpath.domain.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "course")
public class Course extends BaseEntity implements Versionable {

    @Column(name = "image_name")
    private String imageName;
    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_binary")
    @Lob
    private byte[] imageBinary;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_course_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_department_course_id"))
    private DepartmentCourse departmentCourse;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "fk_course_department_id"))
    private Department department;

    private Integer semester;

    private String description;

    @Version
    @Column(name = "version")
    private Long version;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Chapter> chapterList = new ArrayList<>();

    @OneToOne(mappedBy="course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CourseContent courseContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_course_user_id"))
    @Fetch(FetchMode.JOIN)
    public User user;

    @OneToOne(mappedBy = "course", orphanRemoval = true)
    private CoursePublication coursePublication;

}


