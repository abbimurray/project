package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database credentials and URL
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pknv!47A";

    // Static block to ensure the JDBC driver is loaded
    static {
        try {
            // optional for modern JDBC drivers
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Consider logging this exception as well
        }
    }

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }
}
