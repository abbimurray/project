package dao;

import model.PaymentMethod;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDao {

    //CRUD CREATE - ADD PAYMENT METHOD
    public boolean addPaymentMethod(PaymentMethod paymentMethod) {
        int customerID = paymentMethod.getCustomerID();  // Get customerID from payment method
        System.out.println("Attempting to add payment method for customerID: " + customerID); // Debug print
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
                paymentMethod.setSecurityCode(rs.getString("SecurityCode")); // Consider security practices
                paymentMethod.setNameOnCard(rs.getString("NameOnCard"));
                paymentMethods.add(paymentMethod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentMethods;
    }






    //CRUD UPDATE -- UPDATE PAYMENT METHODS
    public boolean updatePaymentMethod(PaymentMethod paymentMethod) {
        String sql = "UPDATE PaymentMethods SET cardNumber = ?, expiry = ?, nameOnCard = ? WHERE paymentMethodID = ? AND customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paymentMethod.getCardNumber()); // Consider security implications
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
