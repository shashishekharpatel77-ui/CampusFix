package service;

import java.util.Queue;
import java.util.Scanner;
import model.Complaint;

/*
 * Admin class handles complaint management.
 */
public class Admin {

    private Scanner sc;

    private Queue<Complaint> complaints;

    public Admin(Scanner sc, Queue<Complaint> complaints) {

        this.sc = sc;
        this.complaints = complaints;
    }

    public void adminMenu() {

        int choice;

        do {

            System.out.println();
            System.out.println();
            System.out.println("ADMIN MENU");
            System.out.println();
            System.out.println("1. View All Complaints");
            System.out.println("2. View Next Complaint");
            System.out.println("3. Process Next Complaint");
            System.out.println("4. Back");
            System.out.println("");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    viewAllComplaints();
                    break;

                case 2:
                    viewNextComplaint();
                    break;

                case 3:
                    processNextComplaint();
                    break;

                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);
    }

    private void viewAllComplaints() {

        System.out.println();
        System.out.println("ALL COMPLAINTS");

        if (complaints.isEmpty()) {

            System.out.println("No complaints available.");
            return;
        }

        for (Complaint complaint : complaints) {

            complaint.displayComplaint();
        }
    }


    private void viewNextComplaint() {

        System.out.println();
        System.out.println("NEXT COMPLAINT");

        if (complaints.isEmpty()) {

            System.out.println("No complaints available.");
            return;
        }

        Complaint nextComplaint = complaints.peek();

        nextComplaint.displayComplaint();
    }


    private void processNextComplaint() {

        System.out.println();
        System.out.println("PROCESS COMPLAINT");

        if (complaints.isEmpty()) {

            System.out.println("No complaints available.");
            return;
        }

        Complaint complaint = complaints.poll();

        complaint.setStatus("Resolved");

        System.out.println("Complaint processed successfully!");

        complaint.displayComplaint();
    }
}