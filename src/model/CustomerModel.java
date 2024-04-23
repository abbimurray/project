//Student number:C00260073, Student name: Abigail Murray, Semester two

package model;

import utils.LoggerUtility;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class CustomerModel {



    // Method to ADD a new customer
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer_accounts (firstName, lastName, email, phone, password,salt) VALUES (?, ?, ?, ?, ?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getPassword());
            pstmt.setString(6, customer.getSalt());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            LoggerUtility.log(Level.SEVERE, "Failed to add customer", e);
            return false;
        }

    }

    // Method to retrieve a customer by email
    public Customer getCustomerByEmail(String email) {
        Customer customer = null;
        String sql = "SELECT * FROM customer_accounts WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerID(rs.getInt("customerID"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setPassword(rs.getString("password"));
                customer.setSalt(rs.getString("salt")); // Retrieve the salt
            }

        }  catch (SQLException e) {
            LoggerUtility.log(Level.SEVERE, "Failed to retrieve customer by email: " + email, e);
        }
        return null;
    }


    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer_accounts SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setInt(5, customer.getCustomerID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LoggerUtility.log(Level.SEVERE, "Failed to update customer by email: ", e);
            return false;
        }
    }
    //delete
    public boolean deleteCustomerByEmail(String email) {
        String sql = "DELETE FROM customer_accounts WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            LoggerUtility.log(Level.SEVERE, "Failed to delete customer  ", e);
            return false;
        }
    }
}//end class
