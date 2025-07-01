import java.util.Scanner;

public class StudentApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    StudentOperations.addStudent();
                    break;
                case 2:
                    StudentOperations.viewStudents();
                    break;
                case 3:
                    StudentOperations.updateStudent();
                    break;
                case 4:
                    StudentOperations.deleteStudent();
                    break;
                case 5:
                    StudentOperations.searchStudentById();
                    break;
                case 6:
                    System.out.println("Exiting... 👋");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 6);

        sc.close();
    }
}
