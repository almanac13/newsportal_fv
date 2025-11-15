package strategy;

import model.Article;
import model.Subscriber;

public class PushStrategy implements NotificationStrategy{
    @Override
    public void sendNotification(Article article, Subscriber subscriber) {
        System.out.println("Sent " + article.getTitle() + " to " + subscriber.getName()+ "to Device" + subscriber.getDeviceId());
    }
}
