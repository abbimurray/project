//Student number:C00260073, Student name: Abigail Murray, Semester two

package controller;
import utils.LoggerUtility;
import java.util.logging.Level;


public class UserSession {
    private static UserSession instance;
    private String userEmail;
    private int customerID;
    private String firstName;
    private String lastName;




    // Private constructor to prevent instantiation
    private UserSession() {
        LoggerUtility.log(Level.INFO, "UserSession instance created");
    }




    // Static method to get instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
            LoggerUtility.log(Level.INFO, "UserSession instance initialized");
        }
        return instance;
    }

    // Getters and setters
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        LoggerUtility.log(Level.INFO, "User email set to: " + userEmail);
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
        LoggerUtility.log(Level.INFO, "Customer ID set to: " + customerID);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        LoggerUtility.log(Level.INFO, "First name set to: " + firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        LoggerUtility.log(Level.INFO, "Last name set to: " + lastName);

    }

    // Method to clear the session
    public void clearSession() {
        LoggerUtility.log(Level.INFO, "Session cleared for user: " + userEmail);
        userEmail = null;
        customerID = 0;
        firstName = null;
        lastName = null;
    }
}//end class