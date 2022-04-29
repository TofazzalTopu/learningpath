package com.gr.learningpath.service.notification;

import com.gr.learningpath.domain.notification.Notification;
import com.gr.learningpath.domain.notification.NotificationStatus;
import com.gr.learningpath.repositories.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    @Override
    public void saveNotification(@Nonnull Notification notification) {
        notificationRepository.save(notification);
    }

    @Transactional
    @Override
    public List<Notification> fetchNotifications(Long userId){
        Optional<List<Notification>> notificationsByUser = notificationRepository.findAllByToUser(userId, NotificationStatus.SENT);
        if (notificationsByUser.isEmpty()) return null;

        List<Notification> notifications = notificationsByUser.get();
        for (Notification notification: notifications) {
            notification.setStatus(NotificationStatus.READ);
            notificationRepository.save(notification);
        }

        return notifications;
    }

}
