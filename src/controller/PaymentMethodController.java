//Student number:C00260073, Student name: Abigail Murray, Semester two
package controller;

import dao.PaymentMethodDao;
import model.PaymentMethod;
import utils.LoggerUtility;
import java.util.logging.Level;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PaymentMethodController {
    private PaymentMethodDao paymentMethodDao;

    public PaymentMethodController() {
        this.paymentMethodDao = new PaymentMethodDao();
    }

    //Adding new payment method, 1st checks the fields are valid
   public String addPaymentMethod(PaymentMethod paymentMethod) {
        String validationResult = validatePaymentMethod(paymentMethod);
        if (!validationResult.isEmpty()) {
            return validationResult;  // Fields are invalid, Return validation error message.
        }
        if (paymentMethodDao.addPaymentMethod(paymentMethod)) {
            return "success";  // Fields are valid
        } else {
            return "Database error occurred while adding payment method.";  // Indicates database failure.
        }
    }



    public List<PaymentMethod> getPaymentMethodsForCustomer(int customerID) {
        try {
            return paymentMethodDao.getPaymentMethodsByCustomerId(customerID);
        } catch (Exception e) {
            LoggerUtility.log(Level.SEVERE, "Failed to retrieve payment methods for customer ID: " + customerID, e);
            return null;
        }
    }


    //Update payment method, checks fields for card number and name
    public boolean updatePaymentMethod(PaymentMethod paymentMethod) {
        try {
            return paymentMethodDao.updatePaymentMethod(paymentMethod);
        } catch (Exception e) {
            LoggerUtility.log(Level.SEVERE, "Failed to update payment method " , e);
            return false;
        }
    }


    //Delete a payment method
    public boolean deletePaymentMethod(int paymentMethodId) {
        try {
            int customerID = UserSession.getInstance().getCustomerID();
            return paymentMethodDao.deletePaymentMethod(paymentMethodId, customerID);
        } catch (Exception e) {
            LoggerUtility.log(Level.SEVERE, "Failed to delete payment method with ID: " + paymentMethodId, e);
            return false;
        }
    }


    //validations for adding a new payment method and for updating

    public String validatePaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod.getCardNumber().isEmpty() ||
                paymentMethod.getNameOnCard().isEmpty() ||
                paymentMethod.getExpiry().isEmpty() ||
                paymentMethod.getSecurityCode().isEmpty()) {
            return "All fields must be entered. Please check your input and try again.";
        }

        if (!paymentMethod.getCardNumber().matches("\\d{16}")) {
            return "Invalid card number. Please enter a valid 16 digit card number.";
        }
        if (!paymentMethod.getNameOnCard().matches("[a-zA-Z\\s'-]+")) {
            return "Invalid name. Please enter a valid name (letters, spaces, or hyphens only).";
        }
        if (!paymentMethod.getExpiry().matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            return "Invalid expiry format. Please use MM/YY format.";
        }
        if (!isValidExpiryDate(paymentMethod.getExpiry())) {
            return "Expiry date cannot be in the past.";
        }
        if (!paymentMethod.getSecurityCode().matches("\\d{3,4}")) {
            return "Invalid security code. Please enter a valid 3 or 4 digit CVV.";
        }

        return ""; // No error
    }


    // the date has to be in the format mm/yy and should be in the future
    private boolean isValidExpiryDate(String expiryDate) {
        try {
            DateFormat formatter = new SimpleDateFormat("MM/yy");  // for MM/YY format
            formatter.setLenient(false);
            Date expiry = formatter.parse(expiryDate);
            Calendar expCal = Calendar.getInstance();
            expCal.setTime(expiry);

            // Set to the last day of the month
            expCal.set(Calendar.DAY_OF_MONTH, expCal.getActualMaximum(Calendar.DAY_OF_MONTH));
            expCal.set(Calendar.HOUR_OF_DAY, 23);
            expCal.set(Calendar.MINUTE, 59);
            expCal.set(Calendar.SECOND, 59);
            expCal.set(Calendar.MILLISECOND, 999);

            // Compare with the current time
            Calendar now = Calendar.getInstance();
            return !expCal.before(now);
        } catch (ParseException e) {
            return false;
        }

    }

}//end class