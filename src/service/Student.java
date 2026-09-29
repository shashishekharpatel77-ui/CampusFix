package service;

import java.util.Queue;
import java.util.Scanner;
import model.Complaint;

public class Student {

    private Scanner sc;

    private final Queue<Complaint> complaints;

    public Student(Scanner sc, Queue<Complaint> complaints) {

        this.sc = sc;
        this.complaints = complaints;
    }

    public void studentMenu() {

        int choice;

        do {

            System.out.println();
            System.out.println("");
            System.out.println("STUDENT MENU");
            System.out.println("");
            System.out.println("1. Submit Complaint");
            System.out.println("2. View Complaint Queue");
            System.out.println("3. Back");
            System.out.println("");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    submitComplaint();
                    break;

                case 2:
                    viewComplaints();
                    break;

                case 3:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 3);
    }

    private void submitComplaint() {

        System.out.println();
        System.out.println("SUBMIT COMPLAINT");

        System.out.print("Enter Complaint ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.println();
        System.out.println("Select Category:");
        System.out.println("1. Electrical");
        System.out.println("2. Classroom");
        System.out.println("3. Cleaning");
        System.out.println("4. Hostel");
        System.out.println("5. Other");

        System.out.print("Enter category: ");
        int categoryChoice = sc.nextInt();
        sc.nextLine();

        String category;

        switch (categoryChoice) {

            case 1:
                category = "Electrical";
                break;

            case 2:
                category = "Classroom";
                break;

            case 3:
                category = "Cleaning";
                break;

            case 4:
                category = "Hostel";
                break;

            default:
                category = "Other";
        }

        System.out.print("Enter Description: ");
        String description = sc.nextLine();

        Complaint complaint =
                new Complaint(
                        id,
                        name,
                        category,
                        description
                );

        complaints.offer(complaint);

        System.out.println();
        System.out.println("Complaint submitted successfully!");
        System.out.println("Complaint ID : " + id);
        System.out.println("Status       : Pending");
    }

    private void viewComplaints() {

        System.out.println();
        System.out.println("COMPLAINT QUEUE");

        if (complaints.isEmpty()) {

            System.out.println("No complaints available.");
            return;
        }

        for (Complaint complaint : complaints) {

            complaint.displayComplaint();
        }
    }
}