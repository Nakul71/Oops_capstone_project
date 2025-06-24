package prescriptionreminder;

import javax.swing.*;

public class DailyReminder extends Reminder {
    public DailyReminder(String time, String message) {
        super(time, message);
    }

    @Override
    public void sendReminder() {
        System.out.println("Daily Reminder: " + message + " at " + time);
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Daily Reminder: " + message + " at " + time, "Daily Reminder", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    @Override
    public void sendReminder(JTextArea reminderArea) {
        String reminderText = "Daily Reminder: " + message + " at " + time + "\n";
        reminderArea.append(reminderText);
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, reminderText, "Daily Reminder", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}