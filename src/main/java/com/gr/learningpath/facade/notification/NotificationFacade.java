package com.gr.learningpath.facade.notification;

import com.gr.learningpath.api.response.notification.NotificationsResponse;
import com.gr.learningpath.domain.notification.Notification;

import java.util.List;

public interface NotificationFacade {

    void createNotification(Notification notification);

    List<NotificationsResponse> getNotifications(Long userId);
}
