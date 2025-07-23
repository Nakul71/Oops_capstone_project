# Prescription Reminder System

A Java Swing-based desktop application designed to help users manage their prescriptions and stay on top of their medication schedule. The application supports multiple types of reminders and provides a simple user-friendly interface.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Data Handling](#data-handling)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)

---

## Features

- User login and authentication system
- Add, edit, and view prescriptions
- Multiple types of reminders:
  - Daily
  - Weekly
  - Emergency
  - SMS-based
  - Push notification
- Simple local file-based storage
- Intuitive and clean GUI with Swing components

---

## Tech Stack

| Layer           | Technology        |
|------------------|-------------------|
| Language          | Java              |
| UI Framework      | Java Swing        |
| Storage           | File System (.txt files) |
| IDE Used          | IntelliJ IDEA     |

---

## Project Structure

```
Oops_Project/
├── src/
│   ├── GUI/                       # GUI Frames
│   └── prescriptionreminder/     # Core logic for reminders
├── data/                         # Local text file storage
│   ├── users.txt
│   └── prescriptions.txt
├── .gitignore
├── Oops_Project.iml
└── README.md
```

## Usage

- Login with valid credentials (stored in `users.txt`)
- Navigate through the dashboard to:
  - Add new prescriptions
  - Edit or View existing ones
- Based on the prescription type, reminders will trigger as needed
- SMS and push notifications are simulated (can be extended)

---

## Data Handling

All data is stored in plain text files:

- `data/users.txt`: Stores user credentials  
- `data/prescriptions.txt`: Stores prescription entries  

You can manually add dummy data or let the GUI handle it.  
Make sure these files are not deleted when running the application.

---

## Future Enhancements

- Replace text files with SQLite or MySQL database
- Real SMS and push notifications using Twilio/Firebase
- Multi-user roles: Doctor & Patient
- Analytics for medication history
- Cloud storage integration

---

## Contributing

Pull requests are welcome.  
For major changes, please open an issue first to discuss what you would like to improve or change.

---

## Author

**Nakul Yadav**  
Cloud Intern | Java Developer