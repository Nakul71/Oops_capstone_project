package prescriptionreminder;

import javax.swing.*;

public class PushNotificationReminder extends Reminder {
    public PushNotificationReminder(String time, String message) {
        super(time, message);
    }

    @Override
    public void sendReminder() {
        System.out.println("App Push Notification: " + message + " at " + time);
    }

    @Override
    public void sendReminder(JTextArea reminderArea) {
        reminderArea.append("App Push Notification: " + message + " at " + time + "\n");
    }
}