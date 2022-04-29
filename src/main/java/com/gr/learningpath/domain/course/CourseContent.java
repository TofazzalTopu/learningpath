package com.gr.learningpath.domain.course;

import com.gr.learningpath.domain.BaseEntity;
import com.gr.learningpath.domain.Versionable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "course_content")

public class CourseContent extends BaseEntity implements Versionable {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_course_id", nullable = true)
    private Course course;

    @Column(name = "relative_url")
    private String relativeUrl;

    @OneToMany(mappedBy = "courseContent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CourseContentDocument> documents;

    @Version
    @Column(name = "version")
    private Long version;
}
