//Student number:C00260073, Student name: Abigail Murray, Semester two
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

    /**
     * Constructs a new PaymentMethodController. This constructor initializes the PaymentMethodDao
     * that this controller will use to interact with the database for payment method operations.
     * It sets up the necessary infrastructure for managing payment methods within the application.
     */

    public PaymentMethodController() {
        this.paymentMethodDao = new PaymentMethodDao();
    }

    //Adding new payment method, 1st checks the fields are valid

    /**
     * to add a new payment method to the database
     * firstly validates the provided details
     *  If anyvalidation errors are found, it returns an error message.
     *  If the validation passes, it will then try to add the payment method into the database through the PaymentMethodDao.
     *
     * @param paymentMethod the PaymentMethod object containing all details to be added. It must not be null.
     * @return a string indicating the result of the operation.
     */
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


    /**
     * Retrieves a list of all payment methods associated with a given customer from the database.
     * If no payment methods are found for the customer, it returns an empty list.
     *
     * @param customerID  of the customer whose payment methods are to be retrieved
     * @return a list of {@code PaymentMethod} objects associated with the specified customer; the list will be empty if no payment methods are found
     */
    public List<PaymentMethod> getPaymentMethodsForCustomer(int customerID) {
        return paymentMethodDao.getPaymentMethodsByCustomerId(customerID);
    }



    //Update payment method, checks fields for card number and name

    /**
     * Updates an existing payment method in the database.
     * This method passes the PaymentMethod object to the {@code PaymentMethodDao} for updating the corresponding record.
     * The PaymentMethod object should include all the fields that need to be updated
     * @param paymentMethod the PaymentMethod object containing updated data for an existing payment method.
     *                      It must include the payment method's ID and all fields that require updates.
     * @return true if the update was successful and affected one or more rows in the database, false otherwise.
     */
    public boolean updatePaymentMethod(PaymentMethod paymentMethod) {

        return paymentMethodDao.updatePaymentMethod(paymentMethod);
    }

    //Delete a payment method

    /**
     *Deletes an existing paymentMethod in the database
     * Retrieves the customerID from the user session and calls the paymentMethodDao to delete the payment method for the specified customer
     * @param paymentMethodId
     * @return
     */
    public boolean deletePaymentMethod(int paymentMethodId) {
        // Retrieve the customerID from the UserSession
        int customerID = UserSession.getInstance().getCustomerID();

        // Call the DAO method to delete the payment method for this customerID
        return paymentMethodDao.deletePaymentMethod(paymentMethodId, customerID);
    }


    //validations for adding a new payment method and for updating

    /**
     * Validates the payment method - validates the card number, name on card, expiry date and security code fields.
     * @param paymentMethod
     * @return String stating if the payment method was invalid
     */

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

    /**
     * checks the expiry date on the card paymentMethod
     * must be in the format mm/yy
     * must also be in the future 
     * @param expiryDate
     * @return true if the expiry date is valid, false otherwise
     */
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