package factory;

import adapter.OldNotificationAdapter;
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
        }else if (strategyType.equalsIgnoreCase("FAX")){
            return new OldNotificationAdapter("fax");
        }else if (strategyType.equalsIgnoreCase("PAPER")){
            return new OldNotificationAdapter("paper");
        }
        else
            return new EmailStrategy();
    }
}
