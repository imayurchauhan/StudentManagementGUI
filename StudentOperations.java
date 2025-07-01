import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class StudentOperations {

    public static void addStudent() {
        try {
            Connection var0 = DBConnect.getConnection();
            Scanner var1 = new Scanner(System.in);
            System.out.print("Enter name: ");
            String var2 = var1.nextLine();
            System.out.print("Enter age: ");
            int var3 = var1.nextInt();
            var1.nextLine();
            System.out.print("Enter course: ");
            String var4 = var1.nextLine();
            PreparedStatement var5 = var0.prepareStatement("INSERT INTO students(name, age, course) VALUES (?, ?, ?)");
            var5.setString(1, var2);
            var5.setInt(2, var3);
            var5.setString(3, var4);
            int var6 = var5.executeUpdate();
            System.out.println(var6 > 0 ? "✅ Student added!" : "❌ Failed to add student.");
            var5.close();
            var0.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
    }

    public static void viewStudents() {
        try {
            Connection var0 = DBConnect.getConnection();
            Statement var1 = var0.createStatement();
            ResultSet var2 = var1.executeQuery("SELECT * FROM students");
            System.out.println("\nID | Name\t| Age | Course");
            System.out.println("--------------------------------------");

            while (var2.next()) {
                System.out.printf("%d | %s\t| %d | %s\n", var2.getInt("id"), var2.getString("name"), var2.getInt("age"), var2.getString("course"));
            }

            var2.close();
            var1.close();
            var0.close();
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    public static void updateStudent() {
        try {
            Connection var0 = DBConnect.getConnection();
            Scanner var1 = new Scanner(System.in);
            System.out.print("Enter ID of student to update: ");
            int var2 = var1.nextInt();
            var1.nextLine();
            System.out.print("Enter new name: ");
            String var3 = var1.nextLine();
            System.out.print("Enter new age: ");
            int var4 = var1.nextInt();
            var1.nextLine();
            System.out.print("Enter new course: ");
            String var5 = var1.nextLine();
            PreparedStatement var6 = var0.prepareStatement("UPDATE students SET name=?, age=?, course=? WHERE id=?");
            var6.setString(1, var3);
            var6.setInt(2, var4);
            var6.setString(3, var5);
            var6.setInt(4, var2);
            int var7 = var6.executeUpdate();
            System.out.println(var7 > 0 ? "✅ Student updated!" : "❌ ID not found.");
            var6.close();
            var0.close();
        } catch (Exception var8) {
            var8.printStackTrace();
        }
    }

    public static void deleteStudent() {
        try {
            Connection var0 = DBConnect.getConnection();
            Scanner var1 = new Scanner(System.in);
            System.out.print("Enter ID of student to delete: ");
            int var2 = var1.nextInt();
            PreparedStatement var3 = var0.prepareStatement("DELETE FROM students WHERE id=?");
            var3.setInt(1, var2);
            int var4 = var3.executeUpdate();
            System.out.println(var4 > 0 ? "✅ Student deleted!" : "❌ ID not found.");
            var3.close();
            var0.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    public static void searchStudentById() {
        try {
            Connection var0 = DBConnect.getConnection();
            Scanner var1 = new Scanner(System.in);
            System.out.print("Enter student ID to search: ");
            int var2 = var1.nextInt();

            PreparedStatement var3 = var0.prepareStatement("SELECT * FROM students WHERE id = ?");
            var3.setInt(1, var2);
            ResultSet var4 = var3.executeQuery();

            if (var4.next()) {
                System.out.println("\nStudent Details:");
                System.out.println("ID     : " + var4.getInt("id"));
                System.out.println("Name   : " + var4.getString("name"));
                System.out.println("Age    : " + var4.getInt("age"));
                System.out.println("Course : " + var4.getString("course"));
            } else {
                System.out.println("❌ No student found with ID: " + var2);
            }

            var4.close();
            var3.close();
            var0.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }
}
