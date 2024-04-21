package OLD_DB_IGNORE.Customer;
/*class for: customer
 * student name: abigail murray
 * student number: c00260073*/
public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;


    /*constructor methods*/
    public Customer() {
    }

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
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    //firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;

    }
    //email
    public String getEmail(){
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    //phone
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    //password
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }


}//end class