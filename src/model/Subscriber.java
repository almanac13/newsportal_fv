package model;

import observer.Observer;
import strategy.NotificationStrategy;

import java.util.ArrayList;
import java.util.List;

public class Subscriber implements Observer {
    private String name;
    private String email;
    private String phoneNumber;
    private String deviceId;
    private NotificationStrategy notificationStrategy;
    private List<String> subscribedCategories;

    public Subscriber(String name, String email, String phoneNumber, String deviceId, NotificationStrategy notificationStrategy) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.deviceId = deviceId;
        this.notificationStrategy = notificationStrategy;
        this.subscribedCategories = new ArrayList<String>();
    }

    public void update(Article article) {
        if(subscribedCategories.contains(article.getCategory())) {
            notificationStrategy.sendNotification(article, this);
        }else{
            System.out.println("Not subscribed to article: " + article.getCategory());
        }
    }

    public void setNotificationStrategy(NotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
        System.out.println("Notification strategy set to " + notificationStrategy);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public NotificationStrategy getNotificationStrategy() {
        return notificationStrategy;
    }

    public List<String> getSubscribedCategories() {
        return subscribedCategories;
    }

    public void setSubscribedCategories(List<String> subscribedCategories) {
        this.subscribedCategories = subscribedCategories;
    }

    public void subscribeToCategory(String category) {
        if(!subscribedCategories.contains(category)) {
            subscribedCategories.add(category);
            System.out.println(name + " is subscribed to " + category);
        }else System.out.println(name +" is already subscribed to " + category);
    }

    public void unsubscribeFromCategory(String category) {
        if(subscribedCategories.contains(category)) {
            subscribedCategories.remove(category);
            System.out.println(name +" is unsubscribed from " + category);
        }else System.out.println(name +" is not subscribed to " + category);
    }
}
