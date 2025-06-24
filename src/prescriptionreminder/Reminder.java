package prescriptionreminder;

import javax.swing.*;

public abstract class Reminder {
    protected String time;
    protected String message;
    protected boolean status;

    public Reminder(String time, String message) {
        this.time = time;
        this.message = message;
        this.status = false;
    }

    // Abstract method to send reminder without UI context
    public abstract void sendReminder();

    // Concrete (non-abstract) method to send reminder with UI context (JTextArea)
    public void sendReminder(JTextArea reminderArea) {
        // Default implementation - subclasses can override this
        System.out.println("Reminder: " + message + " at " + time + " (displayed in UI)");
        if (reminderArea != null) {
            reminderArea.append("Reminder: " + message + " at " + time + "\n");
        }
    }

    // Cancel the reminder
    public void cancelReminder() {
        status = false;
        System.out.println("Reminder cancelled.");
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}