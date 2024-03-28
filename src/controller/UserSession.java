package controller;
public class UserSession {
    private static UserSession instance;
    private String userEmail;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // Method to clear the session
    public void clearSession() {
        this.userEmail = null; // Reset the email to null or any initial value you prefer

    }
    // can Add other user details or session-related methods here
}
