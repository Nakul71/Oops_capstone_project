package prescriptionreminder;

import java.io.IOException;

public class User {
    private String name;
    private int age;
    private String contact;
    private PrescriptionFileHandler fileHandler;

    // Constructor for new or returning users
    public User(String name, int age, String contact) {
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.fileHandler = new PrescriptionFileHandler();
    }

    public User(java.util.Scanner scanner) throws IOException {
        this.fileHandler = new PrescriptionFileHandler();
        System.out.print("Enter your Name: ");
        this.name = scanner.nextLine();

        if (fileHandler.userExists(name)) {
            User existingUser = fileHandler.loadUser(name);
            this.age = existingUser.age;
            this.contact = existingUser.contact;
            System.out.println("\nWelcome back, " + name + "!");
        } else {
            System.out.print("Enter your Age: ");
            this.age = getValidIntegerInput(scanner);
            System.out.print("Enter your Contact Number: ");
            this.contact = scanner.nextLine();
            fileHandler.saveUser(this);
            System.out.println("\nUser registered successfully!");
        }
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getContact() {
        return contact;
    }

    private int getValidIntegerInput(java.util.Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
}