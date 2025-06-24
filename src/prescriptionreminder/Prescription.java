package prescriptionreminder;

public class Prescription {
    private String medicineName;
    private String dosage;
    private String time;
    private String frequency;

    public Prescription(String medicineName, String dosage, String time, String frequency) {
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.time = time;
        this.frequency = frequency;
    }

    public String getDetails() {
        return "Medicine: " + medicineName + " | Dosage: " + dosage + " | Time: " + time + " | Frequency: " + frequency;
    }

    public String getTime() {
        return time;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getFrequency() {
        return frequency;
    }
}