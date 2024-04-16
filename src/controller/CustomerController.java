package controller;

import model.Customer;
import model.CustomerModel;


/*customer controller - handle all logical operations like fetching updating deleting
calls methods from customer model,
doesn't have direct access or sql */
public class CustomerController {
    private CustomerModel customerModel;

    public CustomerController() {
        this.customerModel = new CustomerModel();
    }

    public String updateCustomerDetails(Customer customer) {
        if (!isValidCustomer(customer)) {
            return "Validation failed. Check the provided details.";
        }
        boolean success = customerModel.updateCustomer(customer);
        return success ? "Customer details updated successfully." : "Failed to update customer details.";
    }

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
        String phoneRegex = "^\\d+$";
        if (customer.getPhone() == null || !customer.getPhone().matches(phoneRegex)) {
            return false;
        }

        return true;
    }

    public Customer getCustomerByEmail(String email) {
        return customerModel.getCustomerByEmail(email);
    }


}//end class
