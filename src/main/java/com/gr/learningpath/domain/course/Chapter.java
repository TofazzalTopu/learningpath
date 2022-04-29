package com.gr.learningpath.domain.course;

import com.gr.learningpath.domain.BaseEntity;
import com.gr.learningpath.domain.Versionable;
import com.gr.learningpath.domain.document.Document;
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
@Table(name = "chapter")
public class Chapter extends BaseEntity implements Versionable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_chapter_course_id"))
    private Course course;

    @Column(name = "chapter_title")
    private String chapterTitle;

    @Column(name = "relative_url")
    private String relativeUrl;

    @Column(name = "efforts_points")
    private Integer effortPoints;

    @Version
    @Column(name = "version")
    private Long version;

    @OneToMany(targetEntity = Document.class, cascade = CascadeType.ALL, mappedBy = "chapter", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Document> documents;
}



