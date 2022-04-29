package com.gr.learningpath.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Setter
@Getter
@Entity
@Table(name = "image")
public class ImageModel extends BaseEntity implements Versionable {

    private String name;
    private String type;
    @Column(name = "image_binary")
    @Lob
    private byte[] imageBinary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_image_user_id"))
    private User user;

    @Version
    @Column(name = "version")
    private Long version;

    @Enumerated(EnumType.STRING)
    @Column(name = "image_type")
    private ImageType imageType;
}
