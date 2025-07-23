package GUI;

import prescriptionreminder.Prescription;
import prescriptionreminder.PrescriptionFileHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AddPrescriptionFrame extends JFrame {
    private PrescriptionFileHandler fileHandler;

    public AddPrescriptionFrame(String username) {
        fileHandler = new PrescriptionFileHandler();
        setTitle("Add Prescription -> ");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JTextField medField = new JTextField(20);
        JTextField dosageField = new JTextField(20);
        JTextField timeField = new JTextField(20);
        JTextField freqField = new JTextField(20);

        mainPanel.add(makeInputRow("Medicine Name:", medField));
        mainPanel.add(makeInputRow("Dosage:", dosageField));
        mainPanel.add(makeInputRow("Time (e.g., 08:00 AM):", timeField));
        mainPanel.add(makeInputRow("Frequency:", freqField));

        JLabel noteLabel = new JLabel("<html><div style='font-size:10px;color:#666;'>Time must be in 12-hour format (e.g., 08:00 AM)<br>Frequency can be 'Once daily', 'Twice a day', etc.</div></html>");
        noteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(noteLabel);

        JButton saveBtn = new JButton("💾 Save");
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.setPreferredSize(new Dimension(100, 30));
        saveBtn.setFocusPainted(false);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(saveBtn);

        saveBtn.addActionListener(e -> {
            String medicine = medField.getText().trim();
            String dosage = dosageField.getText().trim();
            String time = timeField.getText().trim();
            String frequency = freqField.getText().trim();

            String timePattern = "^(1[0-2]|0?[1-9]):[0-5][0-9] (AM|PM)$";
            if (!time.matches(timePattern)) {
                JOptionPane.showMessageDialog(this, "Invalid time format! Use HH:MM AM/PM (e.g., 08:00 AM)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (medicine.isEmpty() || dosage.isEmpty() || time.isEmpty() || frequency.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Prescription prescription = new Prescription(medicine, dosage, time, frequency);
                fileHandler.savePrescription(prescription, username);
                JOptionPane.showMessageDialog(this, "Prescription saved successfully!");
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving prescription: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    private JPanel makeInputRow(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 25));
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return panel;
    }
}
