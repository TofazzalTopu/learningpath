package com.gr.learningpath.mapper.notification;

import com.gr.learningpath.api.response.notification.NotificationsResponse;
import com.gr.learningpath.domain.notification.Notification;
import com.gr.learningpath.mapper.Mapper;
import com.gr.learningpath.mapper.MapperRegistry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@RequiredArgsConstructor
public class NotificationsMapper {
    @NonNull
    private final MapperRegistry mapperRegistry;

    @PostConstruct
    private void registerMappers() {
        mapperRegistry.addMapper(Notification.class, NotificationsResponse.class, notificationToNotificationsResponse());
    }


    private Mapper<Notification, NotificationsResponse> notificationToNotificationsResponse() {
        return notification -> NotificationsResponse
                .builder()
                .fromUserId(notification.getFromUser().getId())
                .toUserId(notification.getToUser())
                .message(notification.getMessage())
                .build();
    }

}

