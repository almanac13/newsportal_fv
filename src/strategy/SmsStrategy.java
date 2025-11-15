package strategy;

import model.Article;
import model.Subscriber;

public class SmsStrategy implements NotificationStrategy{
    @Override
    public void sendNotification(Article article, Subscriber subscriber) {
        System.out.println("Sent " + article.getTitle() + " to " + subscriber.getName()+ "By Sms, to phone num:" + subscriber.getPhoneNumber());
    }
}
