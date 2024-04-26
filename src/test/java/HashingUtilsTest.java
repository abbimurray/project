import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.HashingUtils;


class HashingUtilsTest {

    @Test
    void testSaltGeneration() {
        String salt1 = HashingUtils.getSalt();
        String salt2 = HashingUtils.getSalt();
        assertNotNull(salt1);
        assertNotNull(salt2);
        assertNotEquals(salt1, salt2, "Each salt should be unique and random");
    }

    @Test
    void testHashPasswordWithSHA256() {
        String password = "TestPassword123";
        String salt = HashingUtils.getSalt();
        String hashed1 = HashingUtils.hashPasswordWithSHA256(password, salt);
        String hashed2 = HashingUtils.hashPasswordWithSHA256(password, salt);

        assertNotNull(hashed1);
        assertEquals(hashed1, hashed2, "Hashing the same password with the same salt should produce the same hash");

        String differentSalt = HashingUtils.getSalt();
        String hashedWithDifferentSalt = HashingUtils.hashPasswordWithSHA256(password, differentSalt);
        assertNotEquals(hashed1, hashedWithDifferentSalt, "Using a different salt should produce a different hash");
    }
}
