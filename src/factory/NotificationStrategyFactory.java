package factory;

import strategy.EmailStrategy;
import strategy.NotificationStrategy;
import strategy.PushStrategy;
import strategy.SmsStrategy;

public class NotificationStrategyFactory {
    public static NotificationStrategy createNotificationStrategy(String strategyType) {
        if (strategyType.equalsIgnoreCase("EMAIL")) {
            return new EmailStrategy();
        }else if (strategyType.equalsIgnoreCase("SMS")){
            return new SmsStrategy();
        }else if (strategyType.equalsIgnoreCase("PUSH")){
            return new PushStrategy();
        }
        else
            return new EmailStrategy();
    }
}
