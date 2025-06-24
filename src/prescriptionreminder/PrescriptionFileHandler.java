package prescriptionreminder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrescriptionFileHandler {
    private static final String USER_FILE = "data/users.txt";
    private static final String PRESCRIPTION_FILE = "data/prescriptions.txt";

    // Save user
    public void saveUser(User user) throws IOException {
        File file = new File(USER_FILE);
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(user.getName() + "," + user.getAge() + "," + user.getContact() + "\n");
        }
    }

    // Check if user exists
    public boolean userExists(String name) throws IOException {
        File file = new File(USER_FILE);
        if (!file.exists()) return false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data[0].equalsIgnoreCase(name)) return true;
            }
        }
        return false;
    }

    // Load user
    public User loadUser(String name) throws IOException {
        File file = new File(USER_FILE);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data[0].equalsIgnoreCase(name)) {
                    return new User(data[0], Integer.parseInt(data[1]), data[2]);
                }
            }
        }
        return null;
    }

    // Save prescription
    public void savePrescription(Prescription prescription, String username) throws IOException {
        File file = new File(PRESCRIPTION_FILE);
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write("User: " + username + " | " + prescription.getDetails() + "\n");
        }
    }

    // Load prescriptions for a user
    public List<Prescription> loadPrescriptions(String username) throws IOException {
        List<Prescription> prescriptions = new ArrayList<>();
        File file = new File(PRESCRIPTION_FILE);
        if (!file.exists()) return prescriptions;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("User: " + username)) {
                    String[] parts = line.split(" \\| ");
                    if (parts.length > 4) {
                        String medicine = parts[1].replace("Medicine: ", "").trim();
                        String dosage = parts[2].replace("Dosage: ", "").trim();
                        String time = parts[3].replace("Time: ", "").trim();
                        String frequency = parts[4].replace("Frequency: ", "").trim();
                        prescriptions.add(new Prescription(medicine, dosage, time, frequency));
                    }
                }
            }
        }
        return prescriptions;
    }
}