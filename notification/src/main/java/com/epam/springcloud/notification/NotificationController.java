package com.epam.springcloud.notification;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotificationController {
    private final Set<Notification> notifications = new HashSet<>();

    public Notification notify(String user) {
        return new Notification();
    }

    public List<Notification> getNotifications() {
        return Collections.emptyList();
    }
}
