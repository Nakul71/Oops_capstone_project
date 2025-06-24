package prescriptionreminder;

import GUI.ReminderPopup;
import javax.swing.*;

public class WeeklyReminder extends Reminder {
    public WeeklyReminder(String time, String message) {
        super(time, message);
    }

    @Override
    public void sendReminder() {
        System.out.println("Weekly Reminder: " + message + " at " + time);
        SwingUtilities.invokeLater(() -> {
            new ReminderPopup("Weekly Reminder: " + message + " at " + time);
        });
    }

    @Override
    public void sendReminder(JTextArea reminderArea) {
        String reminderText = "Weekly Reminder: " + message + " at " + time + "\n";
        reminderArea.append(reminderText);
        SwingUtilities.invokeLater(() -> {
            new ReminderPopup(reminderText);
        });
    }
}