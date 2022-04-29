package com.gr.learningpath.domain.document;

import com.gr.learningpath.domain.BaseEntity;
import com.gr.learningpath.domain.Versionable;
import com.gr.learningpath.domain.course.Chapter;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "document")
public class Document extends BaseEntity implements Versionable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", foreignKey = @ForeignKey(name = "fk_document_chapter_id"))
    public Chapter chapter;

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
