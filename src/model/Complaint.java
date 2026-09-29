package model;


public class Complaint {

    // Private variables provide encapsulation.
    private int complaintId;
    private String studentName;
    private String category;
    private String description;
    private String status;

    public Complaint(int complaintId,
                     String studentName,
                     String category,
                     String description) {

        this.complaintId = complaintId;
        this.studentName = studentName;
        this.category = category;
        this.description = description;

        // Every new complaint starts as Pending.
        this.status = "Pending";
    }

    // Getter for complaint ID
    public int getComplaintId() {
        return complaintId;
    }

    // Getter for student name
    public String getStudentName() {
        return studentName;
    }

    // Getter for category
    public String getCategory() {
        return category;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Getter for status
    public String getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }    public void displayComplaint() {

        System.out.println("");
        System.out.println("Complaint ID : " + complaintId);
        System.out.println("Student      : " + studentName);
        System.out.println("Category     : " + category);
        System.out.println("Description  : " + description);
        System.out.println("Status       : " + status);
        System.out.println("");
    }
}