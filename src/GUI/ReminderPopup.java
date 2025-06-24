package GUI;

import javax.swing.*;
import java.awt.*;

public class ReminderPopup extends JDialog {
    public ReminderPopup(String message) {
        setTitle("Reminder");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label, BorderLayout.CENTER);

        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(e -> dispose());
        panel.add(okBtn, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}