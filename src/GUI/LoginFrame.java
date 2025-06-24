package GUI;

import prescriptionreminder.PrescriptionFileHandler;
import prescriptionreminder.User;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginFrame extends JFrame {
    private JTextField nameField, ageField, contactField;
    private JButton loginButton;
    private JLabel statusLabel;
    private PrescriptionFileHandler fileHandler;

    public LoginFrame() {
        fileHandler = new PrescriptionFileHandler();
        setTitle("Prescription Reminder - Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Enter Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Enter Age (New Users Only):"));
        ageField = new JTextField();
        add(ageField);

        add(new JLabel("Enter Contact (New Users Only):"));
        contactField = new JTextField();
        add(contactField);

        loginButton = new JButton("Continue");
        add(loginButton);

        statusLabel = new JLabel("");
        add(statusLabel);

        loginButton.addActionListener(e -> handleLogin());
        setVisible(true);
    }

    private void handleLogin() {
        String name = nameField.getText().trim();
        String age = ageField.getText().trim();
        String contact = contactField.getText().trim();

        if (name.isEmpty()) {
            statusLabel.setText("Name is required!");
            return;
        }

        try {
            if (fileHandler.userExists(name)) {
                JOptionPane.showMessageDialog(this, "Welcome back, " + name + "!");
                openDashboard(name);
            } else {
                if (age.isEmpty() || contact.isEmpty()) {
                    statusLabel.setText("Age & Contact are required for new users.");
                    return;
                }
                try {
                    int ageInt = Integer.parseInt(age);
                    User user = new User(name, ageInt, contact);
                    fileHandler.saveUser(user);
                    JOptionPane.showMessageDialog(this, "User registered successfully!");
                    openDashboard(name);
                } catch (NumberFormatException e) {
                    statusLabel.setText("Age must be a number.");
                }
            }
        } catch (IOException e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    private void openDashboard(String username) {
        dispose();
        new DashboardFrame(username);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}