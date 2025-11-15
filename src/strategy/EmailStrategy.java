package strategy;

import model.Article;
import model.Subscriber;

public class EmailStrategy implements NotificationStrategy{
    @Override
    public void sendNotification(Article article, Subscriber subscriber) {
        System.out.println("Sent " + article.getTitle() + " to " + subscriber.getName()+ "Email:" + subscriber.getEmail());
    }
}
