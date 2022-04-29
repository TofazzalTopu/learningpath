package com.gr.learningpath.domain.course;

import com.gr.learningpath.domain.BaseEntity;
import com.gr.learningpath.domain.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "course_publication")
public class CoursePublication extends BaseEntity {

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_course_id"))
    Course course;

    @Column(name = "is_private")
    private boolean isPrivate;

    @OneToMany
    private Set<Member> members = new HashSet<>();
}
