import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import dao.PaymentMethodDao;
import controller.PaymentMethodController;
import model.PaymentMethod;

class PaymentMethodControllerTest {

    @Mock
    private PaymentMethodDao paymentMethodDao;

    @InjectMocks
    private PaymentMethodController controller;

    private PaymentMethod paymentMethod;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        // Create a sample payment method for use in tests
        paymentMethod = new PaymentMethod(1, 1, "1234123412341234", "12/24", "123", "John Doe");
    }

    @Test
    void testAddPaymentMethod_ValidDetails_ReturnsSuccess() {
        // Setup
        when(paymentMethodDao.addPaymentMethod(paymentMethod)).thenReturn(true);

        // Action
        String result = controller.addPaymentMethod(paymentMethod);

        // Assert
        assertEquals("success", result);
        verify(paymentMethodDao).addPaymentMethod(paymentMethod);
    }

    @Test
    void testAddPaymentMethod_InvalidDetails_ReturnsErrorMessage() {
        // Setup
        paymentMethod.setCardNumber("invalid"); // Invalid card number
        String expectedMessage = "Invalid card number. Please enter a valid 16 digit card number.";

        // Action
        String result = controller.addPaymentMethod(paymentMethod);

        // Assert
        assertEquals(expectedMessage, result);
        verify(paymentMethodDao, never()).addPaymentMethod(paymentMethod);
    }

    @Test
    void testGetPaymentMethodsForCustomer() {
        // Setup
        when(paymentMethodDao.getPaymentMethodsByCustomerId(1)).thenReturn(Arrays.asList(paymentMethod));

        // Action
        List<PaymentMethod> results = controller.getPaymentMethodsForCustomer(1);

        // Assert
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(1, results.get(0).getPaymentMethodID());
    }

    @Test
    void testUpdatePaymentMethod_Successful() {
        // Setup
        when(paymentMethodDao.updatePaymentMethod(paymentMethod)).thenReturn(true);

        // Action
        boolean result = controller.updatePaymentMethod(paymentMethod);

        // Assert
        assertTrue(result);
        verify(paymentMethodDao).updatePaymentMethod(paymentMethod);
    }



}
