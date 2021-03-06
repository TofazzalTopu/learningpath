package com.gr.learningpath.service.notification;

import com.gr.learningpath.domain.notification.Notification;
import com.gr.learningpath.domain.notification.NotificationStatus;
import com.gr.learningpath.repositories.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;

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
        List<Notification> notifications = notificationRepository.findAllByToUserAndStatus(userId, NotificationStatus.SENT);
        for (Notification notification: notifications) {
            notification.setStatus(NotificationStatus.READ);
            notificationRepository.save(notification);
        }

        return notifications;
    }

}
