import java.sql.*;

public class DBConnect {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb", "root", "mayur"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
