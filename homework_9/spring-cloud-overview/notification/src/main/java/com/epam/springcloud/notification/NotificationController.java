package com.epam.springcloud.notification;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping
@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    private final Set<Notification> notifications = new HashSet<>();

    @PostMapping
    public Notification notify(@RequestBody String userName) {
        return notificationRepository.add(userName, Notification.Notifier.EMAIL);
    }

    @GetMapping
    public List<Notification> getNotifications() {
        return notificationRepository.getAll();
    }
}
