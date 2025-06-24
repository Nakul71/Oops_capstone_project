package prescriptionreminder;

import GUI.ReminderPopup;
import javax.swing.*;

public class EmergencyReminder extends Reminder {
    public EmergencyReminder(String time, String message) {
        super(time, message);
    }

    @Override
    public void sendReminder() {
        System.out.println("EMERGENCY Reminder: " + message + " at " + time + " (TAKE IMMEDIATELY)");
        SwingUtilities.invokeLater(() -> {
            new ReminderPopup("EMERGENCY Reminder: " + message + " at " + time + " (TAKE IMMEDIATELY)");
        });
    }

    @Override
    public void sendReminder(JTextArea reminderArea) {
        String reminderText = "EMERGENCY Reminder: " + message + " at " + time + " (TAKE IMMEDIATELY)\n";
        reminderArea.append(reminderText);
        SwingUtilities.invokeLater(() -> {
            new ReminderPopup(reminderText);
        });
    }
}