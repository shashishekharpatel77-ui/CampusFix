package main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import model.Complaint;
import service.Admin;
import service.Student;


public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Queue<Complaint> complaints =
                new LinkedList<>();

        try {
            ApiServer.start(complaints);
        } catch (Exception e) {
            System.out.println("Web API could not start: " + e.getMessage());
        }

        Student student =
                new Student(sc, complaints);


        Admin admin =
                new Admin(sc, complaints);

        int choice;

        do {

            System.out.println();
            System.out.println("");
            System.out.println("CAMPUSFIX");
            System.out.println("Smart Campus Complaint System");
            System.out.println("");
            System.out.println("1. Student");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.println("");

            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    student.studentMenu();
                    break;

                case 2:

                    admin.adminMenu();
                    break;

                case 3:

                    System.out.println();
                    System.out.println(
                            "Thank you for using CampusFix!"
                    );
                    break;

                default:

                    System.out.println(
                            "Invalid choice!"
                    );
            }

        } while (choice != 3);

        sc.close();
    }
}