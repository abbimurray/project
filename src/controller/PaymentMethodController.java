package controller;

import dao.PaymentMethodDao;
import model.PaymentMethod;

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
            return "Database error occurred while adding payment method.";  // Indicate database failure.
        }
    }

    public List<PaymentMethod> getPaymentMethodsForCustomer(int customerID) {
        return paymentMethodDao.getPaymentMethodsByCustomerId(customerID);
    }

    //Update payment method, checks fields for card number and name
    public boolean updatePaymentMethod(PaymentMethod paymentMethod) {

        return paymentMethodDao.updatePaymentMethod(paymentMethod);
    }

    //Delete a payment method
    //in delete class ask user are they sure they want to delete before they actually delete
    public boolean deletePaymentMethod(int paymentMethodId) {
        // Retrieve the customerID from the UserSession
        int customerID = UserSession.getInstance().getCustomerID();

        // Call the DAO method to delete the payment method for this customerID
        return paymentMethodDao.deletePaymentMethod(paymentMethodId, customerID);
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
            DateFormat formatter = new SimpleDateFormat("MM/yy");  // Adjusted for MM/YY format
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