import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import utils.ValidationUtils;


class ValidationUtilsTest {

    @Test
    void testValidEmail() {
        assertTrue(ValidationUtils.isValidEmail("example@test.com"));
        assertTrue(ValidationUtils.isValidEmail("user.name@domain.co.in"));
    }

    @Test
    void testInvalidEmail() {
        assertFalse(ValidationUtils.isValidEmail("example@.com"));
        assertFalse(ValidationUtils.isValidEmail("example@test"));
        assertFalse(ValidationUtils.isValidEmail("test@domain.com."));
    }

    // Tests for Password Validation
    @Test
    void testValidPassword() {
        assertTrue(ValidationUtils.isValidPassword("Password@123"));
    }

    @Test
    void testInvalidPassword() {
        assertFalse(ValidationUtils.isValidPassword("pass"));
        assertFalse(ValidationUtils.isValidPassword("12345678"));
        assertFalse(ValidationUtils.isValidPassword("password"));
        assertFalse(ValidationUtils.isValidPassword("password123"));
    }

    // Tests for Name Validation
    @Test
    void testValidName() {
        assertTrue(ValidationUtils.isValidName("John Doe"));
        assertTrue(ValidationUtils.isValidName("Jean-Luc Picard"));
        assertTrue(ValidationUtils.isValidName("José Rodríguez"));
    }

    @Test
    void testInvalidName() {
        assertFalse(ValidationUtils.isValidName("John123"));
        assertFalse(ValidationUtils.isValidName("John_Doe"));
    }

    // Tests for Phone Validation
    @Test
    void testValidPhone() {
        assertTrue(ValidationUtils.isValidPhone("123 456 7890"));
        assertTrue(ValidationUtils.isValidPhone("123-456-7890"));
    }

    @Test
    void testInvalidPhone() {
        assertFalse(ValidationUtils.isValidPhone("1234567abc"));
        assertFalse(ValidationUtils.isValidPhone("phone_number"));
    }
}
