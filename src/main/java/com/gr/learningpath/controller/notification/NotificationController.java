package com.gr.learningpath.controller.notification;

import com.gr.learningpath.api.request.notification.NotificationRequest;
import com.gr.learningpath.api.response.notification.NotificationsResponse;
import com.gr.learningpath.domain.User;
import com.gr.learningpath.domain.notification.Message;
import com.gr.learningpath.domain.notification.Notification;
import com.gr.learningpath.domain.notification.NotificationStatus;
import com.gr.learningpath.exceptions.EntityNotFoundException;
import com.gr.learningpath.facade.notification.NotificationFacade;
import com.gr.learningpath.repositories.UserRepository;
import com.gr.learningpath.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {

    NotificationFacade notificationFacade;

    UserRepository userRepository;

    private NotificationService1 notificationService1;

    // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationController(SimpMessagingTemplate messagingTemplate,
                                  NotificationService1 notificationService1,
                                  NotificationFacade notificationFacade,
                                  UserRepository userRepository){
        this.messagingTemplate = messagingTemplate;
        this.notificationService1 = notificationService1;
        this.notificationFacade = notificationFacade;
        this.userRepository = userRepository;
    }


    /**
     * GET  /notifications  -> show the notifications page.
     */
    @GetMapping("/notifications")
    public List<NotificationsResponse> notifications() {
        User user = userRepository.findByUsername(SecurityUtils.getLoggedInUser().get()).orElseThrow(() -> new EntityNotFoundException(User.class));
        return notificationFacade.getNotifications(user.getId());
    }

    /**
     * POST  /some-action  -> do an action.
     * <p>
     * After the action is performed will be notified UserA.
     */

//    http://localhost:3000/some-action/user2?access_token=01a615c7-bc39-4925-9109-643fcd27e43e
    @PostMapping(value = "/notify")
    @ResponseBody
    public ResponseEntity<?> someAction(@RequestBody NotificationRequest notificationRequest) {

        User fromUser = userRepository
                .findByUsername(SecurityUtils.getLoggedInUser().get())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        User toUser = userRepository.findById(notificationRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        Notification notification = Notification.builder()
                .fromUser(fromUser)
                .toUser(toUser.getId())
                .message(notificationRequest.getMessage())
                .status(NotificationStatus.SENT)
                .build();

        // Send the notification to "UserA" (by username)
//        notificationService.notify(
//                notification, // notification object
//                target.getUsername()                    // username
//        );

        messagingTemplate.convertAndSendToUser(
                toUser.getUsername(),
                "/queue/notify",
                new Message(notification.getMessage())
        );

        notificationFacade.createNotification(notification);

        // Return an http 200 status code
        return new ResponseEntity<>(notification.getMessage(), HttpStatus.OK);
    }
}
