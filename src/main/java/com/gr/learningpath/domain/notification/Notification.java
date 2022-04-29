package com.gr.learningpath.domain.notification;

import com.gr.learningpath.domain.BaseEntity;
import com.gr.learningpath.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "notification")
public class Notification extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_notification_user_id"))
    private User fromUser;

    private Long toUser;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

}
