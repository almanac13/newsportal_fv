package adapter;

import model.Article;
import model.Subscriber;
import strategy.NotificationStrategy;

public class OldNotificationAdapter implements NotificationStrategy {
    private OldNotificationSystem oldNotificationSystem;
    private String channel;

    public OldNotificationAdapter(String channel){
        this.oldNotificationSystem = new OldNotificationSystem();
        this.channel = channel;
        System.out.println("Adapter created " + channel);
    }

    @Override
    public void sendNotification(Article article, Subscriber subscriber) {
        String message = article.getTitle() + " " + article.getContent() + " " + article.getCategory() + " " + article.getPriority();
        String recipient = subscriber.getName();

        oldNotificationSystem.sendOldStyleNotification(message, recipient, channel);
        oldNotificationSystem.logOldNotification(
                "Sent to " + recipient + " on " + channel + " for " + article.getTitle() + " " + article.getContent()
        );
    }

    public String getChannel() {
        return channel;
    }
}
