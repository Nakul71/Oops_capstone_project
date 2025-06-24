package prescriptionreminder;

import GUI.ReminderPopup;
import javax.swing.*;

public class SMSReminder extends Reminder {

    public SMSReminder(String time, String message) {
        super(time, message);
    }

    @Override
    public void sendReminder() {
        System.out.println("SMS Reminder: " + message + " at " + time + " (Sending SMS)");
        new ReminderPopup("SMS Reminder: " + message + " at " + time + " (Simulating SMS)");
        // In a real application, you would implement the SMS sending logic here
    }

    @Override
    public void sendReminder(JTextArea reminderArea) {
        String reminderText = "SMS Reminder: " + message + " at " + time + " (SMS Sent)\n";
        reminderArea.append(reminderText);
    }
}