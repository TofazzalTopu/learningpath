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
@Table(name = "user_connections")
public class Follow extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_follow_user_id"))
    private User fromUser;

    private Long toUser;

}
