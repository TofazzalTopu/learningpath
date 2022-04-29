package com.gr.learningpath.domain.course;

import com.gr.learningpath.domain.BaseEntity;
import com.gr.learningpath.domain.Versionable;
import com.gr.learningpath.domain.document.DocumentType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "course_content_document")
public class CourseContentDocument extends BaseEntity implements Versionable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_content_id", foreignKey = @ForeignKey(name = "fk_document_course_content_id"))
    public CourseContent courseContent;

    @Column(name = "doc_name")
    private String docName;

    @Column(name = "doc_file_type")
    private String docFileType;

    @Column(name = "doc_binary")
    @Lob
    private byte[] data;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_content_category")
    private DocumentType docContentCategory;

    @Version
    @Column(name = "version")
    private Long version;
}
