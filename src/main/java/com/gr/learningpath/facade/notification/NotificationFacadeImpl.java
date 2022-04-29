package com.gr.learningpath.facade.notification;

import com.gr.learningpath.api.response.notification.NotificationsResponse;
import com.gr.learningpath.domain.notification.Notification;
import com.gr.learningpath.mapper.MapperRegistry;
import com.gr.learningpath.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationFacadeImpl implements NotificationFacade {

    private final NotificationService notificationService;

    private final MapperRegistry mapperRegistry;

    @Transactional
    @Override
    public void createNotification(Notification notification){
        notificationService.saveNotification(notification);
    }

    @Transactional
    @Override
    public List<NotificationsResponse> getNotifications(Long userId){

        List<Notification> notifications = notificationService.fetchNotifications(userId);
        List<NotificationsResponse> notificationsResponses = new ArrayList<>();

        if (notifications == null) return notificationsResponses;

        for (Notification notification: notifications){
            notificationsResponses.add(mapperRegistry.getMapper(Notification.class, NotificationsResponse.class).map(notification));
        }

        return notificationsResponses;
    }


}
