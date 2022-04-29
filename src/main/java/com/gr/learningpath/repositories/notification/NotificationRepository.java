package com.gr.learningpath.repositories.notification;

import com.gr.learningpath.domain.notification.Notification;
import com.gr.learningpath.domain.notification.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

//    Optional<Notification> save(final Notification notification);

    @Query("SELECT n FROM Notification n WHERE n.toUser = ?1 AND n.status = ?2")
    Optional<List<Notification>> findAllByToUser(Long userId, NotificationStatus status);


}
