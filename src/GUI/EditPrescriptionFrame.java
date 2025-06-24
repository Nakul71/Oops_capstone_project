package GUI;

import prescriptionreminder.Prescription;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EditPrescriptionFrame extends JFrame {
    public EditPrescriptionFrame(String username, String oldPrescription, String medicine, String dosage, String time, String frequency, ViewPrescriptionFrame parent) {
        setTitle("Edit Prescription");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        JTextField medField = new JTextField(medicine);
        JTextField dosageField = new JTextField(dosage);
        JTextField timeField = new JTextField(time);
        JTextField freqField = new JTextField(frequency);

        add(new JLabel("Medicine Name:"));
        add(medField);
        add(new JLabel("Dosage:"));
        add(dosageField);
        add(new JLabel("Time (e.g., 08:00 AM):"));
        add(timeField);
        add(new JLabel("Frequency:"));
        add(freqField);

        String timePattern = "^(1[0-2]|0?[1-9]):[0-5][0-9] (AM|PM)$";
        if (!time.matches(timePattern)) {
            JOptionPane.showMessageDialog(this, "Invalid time format (e.g., 08:00 AM)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JButton saveBtn = new JButton("Save Changes");
        add(saveBtn);

        saveBtn.addActionListener(e -> {
            String newMedicine = medField.getText().trim();
            String newDosage = dosageField.getText().trim();
            String newTime = timeField.getText().trim();
            String newFrequency = freqField.getText().trim();

            if (newMedicine.isEmpty() || newDosage.isEmpty() || newTime.isEmpty() || newFrequency.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<String> lines = new ArrayList<>();
            File file = new File("data/prescriptions.txt");
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.equals(oldPrescription)) {
                        Prescription newPrescription = new Prescription(newMedicine, newDosage, newTime, newFrequency);
                        lines.add("User: " + username + " | " + newPrescription.getDetails());
                    } else {
                        lines.add(line);
                    }
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (FileWriter writer = new FileWriter(file)) {
                for (String line : lines) {
                    writer.write(line + "\n");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            parent.refreshList();
            JOptionPane.showMessageDialog(this, "Prescription updated!");
            dispose();
        });

        setVisible(true);
    }
}
