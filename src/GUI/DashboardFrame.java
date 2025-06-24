package GUI;

import prescriptionreminder.*;
import prescriptionreminder.EmergencyReminder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardFrame extends JFrame {
    private String username;
    private JTextArea reminderArea;
    private PrescriptionFileHandler fileHandler;

    public DashboardFrame(String username) {
        this.username = username;
        this.fileHandler = new PrescriptionFileHandler();
        setTitle("Prescription Reminder | Welcome " + username);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Dashboard", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        reminderArea = new JTextArea();
        reminderArea.setEditable(false);
        reminderArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        reminderArea.setBackground(new Color(245, 245, 245));
        reminderArea.setBorder(BorderFactory.createTitledBorder("Live Reminders"));
        add(new JScrollPane(reminderArea), BorderLayout.EAST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 20, 20));
        centerPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        JButton addBtn = new JButton("➕ Add Prescription");
        JButton viewBtn = new JButton("📋 View & Edit Prescriptions");
        JButton exitBtn = new JButton("🚪 Exit");

        styleButton(addBtn);
        styleButton(viewBtn);
        styleButton(exitBtn);

        addBtn.addActionListener(e -> new AddPrescriptionFrame(username));
        viewBtn.addActionListener(e -> new ViewPrescriptionFrame(username));
        exitBtn.addActionListener(e -> System.exit(0));

        centerPanel.add(addBtn);
        centerPanel.add(viewBtn);
        centerPanel.add(exitBtn);

        add(centerPanel, BorderLayout.CENTER);

        startReminderChecker();
        setVisible(true);
    }

    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(new Color(59, 89, 182));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void startReminderChecker() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000); // Check every minute
                    String currentTime = new SimpleDateFormat("hh:mm a").format(new Date());
                    List<Reminder> reminders = loadReminders();

                    System.out.println("Current Time (IST): [" + currentTime + "]");

                    // Update GUI on EDT
                    SwingUtilities.invokeLater(() -> {
                        reminderArea.setText(""); // Clear previous reminders
                        boolean reminderFound = false;

                        for (Reminder reminder : reminders) {
                            System.out.println("Reminder Time: [" + reminder.getTime() + "]");
                            if (reminder.getTime().equals(currentTime)) { // Using .equals() for exact match
                                System.out.println("*** EXACT MATCH FOUND! *** Reminder Time: [" + reminder.getTime() + "], Current Time (IST): [" + currentTime + "]");
                                System.out.println("Sending reminder: " + reminder.getMessage());
                                reminder.sendReminder(reminderArea);
                                Toolkit.getDefaultToolkit().beep();
                                reminderFound = true;
                            } else if (reminder.getTime().equalsIgnoreCase(currentTime)) {
                                System.out.println("*** CASE-INSENSITIVE MATCH FOUND! *** Reminder Time: [" + reminder.getTime() + "], Current Time (IST): [" + currentTime + "]");
                                System.out.println("Sending reminder (case-insensitive): " + reminder.getMessage());
                                reminder.sendReminder(reminderArea);
                                Toolkit.getDefaultToolkit().beep();
                                reminderFound = true;
                            } else {
                                System.out.println("NO MATCH. Reminder Time: [" + reminder.getTime() + "], Current Time (IST): [" + currentTime + "]");
                            }
                        }

                        if (!reminderFound) {
                            reminderArea.setText("No reminders at this time.");
                        }
                    });
                } catch (InterruptedException e) {
                    System.err.println("Reminder thread interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt(); // Restore interrupted status
                } catch (Exception e) {
                    System.err.println("Reminder error: " + e.getMessage());
                    SwingUtilities.invokeLater(() ->
                            reminderArea.setText("Reminder error: " + e.getMessage())
                    );
                }
            }
        }).start();
    }

    private List<Reminder> loadReminders() {
        List<Reminder> reminders = new ArrayList<>();
        try {
            List<Prescription> prescriptions = fileHandler.loadPrescriptions(username);
            System.out.println("Number of prescriptions loaded for " + username + ": " + prescriptions.size()); // ADD THIS LINE
            for (Prescription p : prescriptions) {
                String message = "Take " + p.getMedicineName() + " (" + p.getDosage() + ")";
                Reminder reminder;
                String prescriptionTimeLower = p.getTime().toLowerCase(); // Convert to lowercase

                switch (p.getFrequency().toLowerCase()) {
                    case "daily":
                        reminder = new DailyReminder(prescriptionTimeLower, message);
                        break;
                    case "weekly":
                        reminder = new WeeklyReminder(prescriptionTimeLower, message);
                        break;
                    case "emergency":
                        reminder = new EmergencyReminder(prescriptionTimeLower, message);
                        break;
                    default:
                        continue; // Skip unknown frequencies
                }
                reminders.add(reminder);
            }
        } catch (IOException e) {
            System.err.println("Error loading reminders: " + e.getMessage());
        }
        System.out.println("Number of reminders created: " + reminders.size()); // ADD THIS LINE
        return reminders;
    }
}
