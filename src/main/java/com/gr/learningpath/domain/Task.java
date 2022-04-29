package com.gr.learningpath.domain;

import com.gr.learningpath.domain.course.Chapter;
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
@Table(name = "task")
public class Task extends BaseEntity implements Versionable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", foreignKey = @ForeignKey(name = "fk_task_chapter_id"))
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", foreignKey = @ForeignKey(name = "fk_task_session_id"))
    private Session session;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    private String description;

    @Version
    @Column(name = "version")
    private Long version;
}
