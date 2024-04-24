//Student number:C00260073, Student name: Abigail Murray, Semester two

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerModel {
    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
    private final String DATABASE_USER = "root";
    private final String DATABASE_PASSWORD = "pknv!47A";


    // Method to RETRIEVE a customer by email

    /**
     * Retrieves the customer based off of email provided. Connects with database and gets the customer based off their email address. this is because this is the field they login with.
     * @param email
     * @return  returns details of teh customer
     */
    public Customer getCustomerByEmail(String email) {
        Customer customer = null;
        String sql = "SELECT * FROM customer_accounts WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
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
                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    // Method to ADD a new customer

    /**
     * Add a new customer to the database. Take in all needed parameters bwlow an dinserts new customer record into the customer_accounts table.
     * @param customer the Customer object containing the data to be added to the database
     * @return true if the record was successfully added (i.e., one row was affected), false if the insertion failed
     */
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer_accounts (firstName, lastName, email, phone, password,salt) VALUES (?, ?, ?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
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
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Updates the customer details
     * @param customer customer object
     * @return true if record was successfully updated, false if customer details could not be updated
     */

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer_accounts SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE customerID = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setInt(5, customer.getCustomerID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    //delete

    /**
     * Deletes a customer from the customer_accounts table in the database, based off the customer email which is provided.
     * @param email
     * @return true if successfully deleted, false if not successfully deleted
     */
    public boolean deleteCustomerByEmail(String email) {
        String sql = "DELETE FROM customer_accounts WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}//end class