package GUI;

import prescriptionreminder.Prescription;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewPrescriptionFrame extends JFrame {
    private String username;
    private JList<String> prescriptionList;
    private DefaultListModel<String> listModel;

    public ViewPrescriptionFrame(String username) {
        this.username = username;
        setTitle("Your Prescriptions");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        prescriptionList = new JList<>(listModel);
        loadPrescriptions();

        JScrollPane scrollPane = new JScrollPane(prescriptionList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton editBtn = new JButton("Edit Selected");
        JButton deleteBtn = new JButton("Delete Selected");
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        editBtn.addActionListener(e -> editPrescription());
        deleteBtn.addActionListener(e -> deletePrescription());

        setVisible(true);
    }

    private void loadPrescriptions() {
        listModel.clear();
        File file = new File("data/prescriptions.txt");
        if (!file.exists()) {
            listModel.addElement("No prescriptions found.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("User: " + username)) {
                    listModel.addElement(line);
                }
            }
        } catch (FileNotFoundException e) {
            listModel.addElement("Error loading prescriptions: " + e.getMessage());
        }
    }

    private void editPrescription() {
        String selected = prescriptionList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a prescription to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] parts = selected.split(" \\| ");
        if (parts.length < 5) return;

        String medicine = parts[1].replace("Medicine: ", "").trim();
        String dosage = parts[2].replace("Dosage: ", "").trim();
        String time = parts[3].replace("Time: ", "").trim();
        String frequency = parts[4].replace("Frequency: ", "").trim();

        new EditPrescriptionFrame(username, selected, medicine, dosage, time, frequency, this);
    }

    private void deletePrescription() {
        String selected = prescriptionList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a prescription to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> lines = new ArrayList<>();
        File file = new File("data/prescriptions.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.equals(selected)) {
                    lines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error reading file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        loadPrescriptions();
        JOptionPane.showMessageDialog(this, "Prescription deleted!");
    }

    public void refreshList() {
        loadPrescriptions();
    }
}