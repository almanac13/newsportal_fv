package strategy;

import model.Article;
import model.Subscriber;

public interface NotificationStrategy {
    void sendNotification(Article article, Subscriber subscriber);
}
