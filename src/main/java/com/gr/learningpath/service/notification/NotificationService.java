package com.gr.learningpath.service.notification;

import com.gr.learningpath.domain.notification.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    void saveNotification(Notification notification);

    List<Notification> fetchNotifications(Long userId);
}
