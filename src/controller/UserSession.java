//Student number:C00260073, Student name: Abigail Murray, Semester two

package controller;

public class UserSession {
    private static UserSession instance;
    private String userEmail;
    private int customerID;
    private String firstName;
    private String lastName;

    // Private constructor to prevent instantiation
    private UserSession() {}

    // Static method to get instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Getters and setters
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Method to clear the session
    public void clearSession() {
        userEmail = null;
        customerID = 0;
        firstName = null;
        lastName = null;
    }
}//end class