package com.gr.learningpath.repositories.notification;

import com.gr.learningpath.domain.notification.Notification;
import com.gr.learningpath.domain.notification.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

//    Optional<Notification> save(final Notification notification);

//    @Query("SELECT n FROM Notification n WHERE n.toUser = ?1 AND n.status = ?2")
    List<Notification> findAllByToUserAndStatus(Long userId, NotificationStatus status);


}
