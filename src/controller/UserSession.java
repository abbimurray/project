//Student number:C00260073, Student name: Abigail Murray, Semester two

package controller;

public class UserSession {
    private static UserSession instance;
    private String userEmail;
    private int customerID;
    private String firstName;
    private String lastName;

    // Private constructor to prevent instantiation

    /**
     * Default constructor for UserSession
     */
    private UserSession() {}

    // Static method to get instance

    /**
     * This method ensures that the UserSession class has only one instance throughout the application.
     * It checks if the instance is null, and if so, creates a new instance of UserSession.
     * Otherwise, it returns the existing instance.
     *
     * @return UserSession The single, static instance of the UserSession class.
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Getters and setters

    /**
     * Getter method to get the user email -- userdession
     * @return userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Setter method to set the userEmail
     * @param userEmail
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * getter method to get customerID  -- user session
     * @return customerID
     */

    public int getCustomerID() {
        return customerID;
    }

    /**
     * setter method  fpr customerID -- user session
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter method to get the first name of the customer -- user session
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter method to set the first name of the customer -- user session
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



    /**
     * getter method to get the last name of the customer -- user session
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setter method to set the last name of the customer -- user session
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Method to clear the session

    /**
     * method to clear users information
     */
    public void clearSession() {
        userEmail = null;
        customerID = 0;
        firstName = null;
        lastName = null;
    }
}//end class