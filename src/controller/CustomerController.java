//Student number:C00260073, Student name: Abigail Murray, Semester two

package controller;

//imports
import model.Customer;
import model.CustomerModel;
import utils.LoggerUtility;
import java.util.logging.Level;

public class CustomerController {
    private CustomerModel customerModel;

    public CustomerController() {
        this.customerModel = new CustomerModel();
    }


    public String updateCustomerDetails(Customer customer) {
        try {
            if (!isValidCustomer(customer)) {
                LoggerUtility.log(Level.WARNING, "Validation failed for customer: " + customer);
                return "Validation failed. Ensure all fields are filled in, names are alphabetical characters, and phone number contains only digits, spaces, dashes, or brackets.";
            }
            boolean success = customerModel.updateCustomer(customer);
            if (success) {
                LoggerUtility.log(Level.INFO, "Customer details updated successfully for: " + customer);
                return "Customer details updated successfully.";
            } else {
                LoggerUtility.log(Level.SEVERE, "Failed to update customer details for: " + customer);
                return "Failed to update customer details.";
            }
        } catch (Exception e) {
            LoggerUtility.log(Level.SEVERE, "Error updating customer details for: " + customer, e);
            return "An error occurred while updating details.";
        }
    }

    private boolean isValidCustomer(Customer customer) {
        // Validate first name and last name (allowing alphabetic characters, spaces, hyphens, and apostrophes)
        String nameRegex = "^[a-zA-Z\\s'-]+$";
        if (customer.getFirstName() == null || !customer.getFirstName().trim().matches(nameRegex) ||
                customer.getLastName() == null || !customer.getLastName().trim().matches(nameRegex)) {
            return false;
        }

        // Validate email using regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        if (customer.getEmail() == null || !customer.getEmail().matches(emailRegex)) {
            return false;
        }

        // Validate phone number to ensure it consists only of digits and spaces (optional dashes or parentheses)
        String phoneRegex = "^[\\d\\s()-]+$";
        if (customer.getPhone() == null || !customer.getPhone().matches(phoneRegex)) {
            return false;
        }

        return true;
    }


    public Customer getCustomerByEmail(String email) {
        try {
            Customer customer = customerModel.getCustomerByEmail(email);
            if (customer == null) {
                LoggerUtility.log(Level.WARNING, "No customer found with email: " + email);
            }
            return customer;
        } catch (Exception e) {
            LoggerUtility.log(Level.SEVERE, "Error fetching customer by email: " + email, e);
            return null;
        }
    }


}//end class
