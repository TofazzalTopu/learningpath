package com.gr.learningpath.domain;

import com.gr.learningpath.domain.course.Chapter;
import com.gr.learningpath.domain.group.Group;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "session")

public class Session extends BaseEntity implements Versionable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", foreignKey = @ForeignKey(name = "fk_session_chapter_id"))
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_group_id", foreignKey = @ForeignKey(name = "fk_session_student_group_id"))
    private Group group;

    @Column(name = "session_title")
    private String title;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "description")
    private String description;

    private LocalDate date;

    @Version
    @Column(name = "version")
    private Long version;
}
