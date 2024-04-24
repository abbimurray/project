//Student number:C00260073, Student name: Abigail Murray, Semester two

package model;

public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String salt; //  field for salt - needed for password hashing



    //constructor methods

    /**
     * this is the default constructor for customer
     */
    public Customer() {
    }

    /**
     * Constructor method for customer which takes parameters
     * @param customerID
     * @param firstName
     * @param lastName
     * @param email
     * @param phone
     * @param password
     */
    public Customer(int customerID, String firstName, String lastName, String email, String phone, String password) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Getters and setters

    //customerID

    /**
     * getter method to return the customerID
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * setter method to set the customerID
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    //firstName

    /**
     * getter method to return the firstname of the customer
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter method to set the firstname of the customer
     * @param firstName
     */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //lastName

    /**
     * getter method to return the lastname of the customer
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setter method to set the lastname of the customer
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;

    }
    //email

    /**
     * getter method to return the email of the user
     * @return email
     */
    public String getEmail(){
        return email;
    }

    /**
     * setter method to set  the email of the customer
     * @param email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    //phone

    /**
     * getter method to return te phone number of the customer
     * @return phone
     */
    public String getPhone(){
        return phone;
    }

    /**
     * setter method to set the phone number of the customer
     * @param phone
     */
    public void setPhone(String phone){
        this.phone = phone;
    }

    //password

    /**
     * getter method to return the password of the customer
     * @return password
     */
    public String getPassword(){
        return password;
    }

    /**
     * setter method to set the password of the customer
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * getter method to return the salt - used with passwords
     * @return salt
     */

    public String getSalt() {
        return salt;
    }

    /**
     * setter method to set the salt of the customer used in passwords
     * @param salt
     */

    public void setSalt(String salt) {
        this.salt = salt;
    }

}//end class