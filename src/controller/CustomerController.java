//Student number:C00260073, Student name: Abigail Murray, Semester two

package controller;

//imports
import model.Customer;
import model.CustomerModel;



public class CustomerController {
    private CustomerModel customerModel;

    /**
     * Constructs a new instance of CustomerController. This constructor initializes a new
     * CustomerModel to handle data operations related to customers. It ensures that the
     * controller has all necessary setups to manage customer data interactions effectively.
     */

    public CustomerController() {
        this.customerModel = new CustomerModel();
    }

    /**
     *Updates the details of an existing customer in the customer_accounts table. Checks firstly that the details are validated.
     * If the details are valid, updates the customers details. if not valid it will not update the details
     * @param customer
     * @return String syaing if the validation was successful or not
     */

    public String updateCustomerDetails(Customer customer) {
        if (!isValidCustomer(customer)) {
            return "Validation failed. Check the provided details.";
        }
        boolean success = customerModel.updateCustomer(customer);
        return success ? "Customer details updated successfully." : "Failed to update customer details.";
    }

    /**
     * Method to check if the customer is valid.
     * The customer is valid if all fields such as lastname,firstname and phone number meet the required format
     * @param customer
     * @return true if the customer details are valid, false otherwise
     */
    private boolean isValidCustomer(Customer customer) {
        // Validate first name and last name (allowing alphabetic characters, spaces, hyphens, and apostrophes)
        String nameRegex = "^[a-zA-Z\\s'-]+$";
        if (customer.getFirstName() == null || !customer.getFirstName().trim().matches(nameRegex) ||
                customer.getLastName() == null || !customer.getLastName().trim().matches(nameRegex)) {
            return false;
        }

        // Validate email using a more comprehensive regex pattern
        String emailRegex = "^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        if (customer.getEmail() == null || !customer.getEmail().matches(emailRegex)) {
            return false;
        }

        // Validate phone number to ensure it consists only of digits

        String phoneRegex = "^[\\d\\s()-]+$";

        if (customer.getPhone() == null || !customer.getPhone().matches(phoneRegex)) {
            return false;
        }

        return true;
    }

    /**
     * Get the customer by email
     * @param email the email address used to search for the customer. It must be a valid email format.
     * @return the Customer object associated with the given email if found; otherwise, null.
     */

    public Customer getCustomerByEmail(String email) {
        return customerModel.getCustomerByEmail(email);
    }


}//end class