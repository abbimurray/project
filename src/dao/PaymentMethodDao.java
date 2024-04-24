//Student number:C00260073, Student name: Abigail Murray, Semester two

package dao;

import model.PaymentMethod;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDao {

    //CRUD CREATE - ADD PAYMENT METHOD
    /**
     * Adds a new payment method to the database.
     * @param paymentMethod
     * @return true if the record was successfully added (i.e., one row was affected), false if the insertion failed
     */
    public boolean addPaymentMethod(PaymentMethod paymentMethod) {
        int customerID = paymentMethod.getCustomerID();  // Get customerID from payment method
        System.out.println("Attempting to add payment method for customerID: " + customerID); //print to terminal, help debug
        String sql = "INSERT INTO PaymentMethods (customerID, cardNumber, expiry, securityCode, nameOnCard) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, paymentMethod.getCustomerID());
            pstmt.setString(2, paymentMethod.getCardNumber());
            pstmt.setString(3, paymentMethod.getExpiry());
            pstmt.setString(4, paymentMethod.getSecurityCode());
            pstmt.setString(5, paymentMethod.getNameOnCard());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //CRUD RETRIEVE - VIEW PAYMENT METHODS

    /**
     * Get the payment methods for a specific customer from paymentMethod table in the database based on the customerId provided
     * @param customerID
     * @return  list of payment methods for the specified customer (customer that is logged in)
     */
    public List<PaymentMethod> getPaymentMethodsByCustomerId(int customerID) {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        String sql = "SELECT * FROM PaymentMethods WHERE customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setPaymentMethodID(rs.getInt("PaymentMethodID"));
                paymentMethod.setCustomerID(rs.getInt("CustomerID"));
                paymentMethod.setCardNumber(rs.getString("CardNumber"));
                paymentMethod.setExpiry(rs.getString("Expiry"));
                paymentMethod.setSecurityCode(rs.getString("SecurityCode"));
                paymentMethod.setNameOnCard(rs.getString("NameOnCard"));
                paymentMethods.add(paymentMethod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentMethods;
    }


    //CRUD UPDATE -- UPDATE PAYMENT METHODS

    /**
     * Update the details of the payment method for a specific payment method selected.
     * @param paymentMethod
     * @return true if the payment methods were updated, false otherwise
     */
    public boolean updatePaymentMethod(PaymentMethod paymentMethod) {
        String sql = "UPDATE PaymentMethods SET cardNumber = ?, expiry = ?, nameOnCard = ? WHERE paymentMethodID = ? AND customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paymentMethod.getCardNumber());
            pstmt.setString(2, paymentMethod.getExpiry());
            pstmt.setString(3, paymentMethod.getNameOnCard());
            pstmt.setInt(4, paymentMethod.getPaymentMethodID());
            pstmt.setInt(5, paymentMethod.getCustomerID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    //CRUD DELETE -- DELETE PAYMENT METHODS

    /**
     * Deletes a payment method based on the paymentID provided and for the logged in customer
     * @param paymentMethodID
     * @param customerID
     * @return true if the payment method was deleted, false if the payment method was not deleted
     */
    public boolean deletePaymentMethod(int paymentMethodID, int customerID) {
        String sql = "DELETE FROM PaymentMethods WHERE paymentMethodID = ? AND customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, paymentMethodID);
            pstmt.setInt(2, customerID);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}//END CLASS
