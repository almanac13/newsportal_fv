package adapter;

public class OldNotificationSystem {
    public void sendOldStyleNotification(String message, String recipient, String channel) {
        System.out.println( message +" sent by old style to " + recipient + " channel: " + channel);
    }
    public void logOldNotification(String info){
        System.out.println("Old log:" + info);
    }
}
