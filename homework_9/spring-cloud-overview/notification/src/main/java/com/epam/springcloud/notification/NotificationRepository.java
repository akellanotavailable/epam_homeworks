package com.epam.springcloud.notification;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationRepository {

    List<Notification> notifications = new ArrayList<>();

    public Notification add(String userName, Notification.Notifier notifier){
        Notification notification = new Notification();
        notification.setUser(userName);
        notification.setNotifyBy(notifier);
        notifications.add(notification);
        return notification;


    }

    public List<Notification> getAll(){
        return notifications;
    }
}
