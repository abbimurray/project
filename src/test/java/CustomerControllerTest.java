import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import model.CustomerModel;
import controller.CustomerController;
import model.Customer;


class CustomerControllerTest {

    @Mock
    private CustomerModel customerModel;

    @InjectMocks
    private CustomerController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testUpdateCustomerDetailsValid() {
        Customer validCustomer = new Customer();
        validCustomer.setFirstName("John");
        validCustomer.setLastName("Doe");
        validCustomer.setEmail("john.doe@example.com");
        validCustomer.setPhone("1234567890");

        when(customerModel.updateCustomer(validCustomer)).thenReturn(true);

        String result = controller.updateCustomerDetails(validCustomer);
        assertEquals("Customer details updated successfully.", result);
    }

    @Test
    void testUpdateCustomerDetailsInvalid() {
        Customer invalidCustomer = new Customer();
        invalidCustomer.setFirstName("John123"); // Invalid first name

        String result = controller.updateCustomerDetails(invalidCustomer);
        assertEquals("Validation failed. Check the provided details.", result);
    }

    @Test
    void testGetCustomerByEmail() {
        Customer customer = new Customer();
        customer.setEmail("user@example.com");

        when(customerModel.getCustomerByEmail("user@example.com")).thenReturn(customer);

        Customer result = controller.getCustomerByEmail("user@example.com");
        assertNotNull(result);
        assertEquals("user@example.com", result.getEmail());
    }
}