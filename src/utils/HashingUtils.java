//Student number:C00260073, Student name: Abigail Murray, Semester two

//this class is used with the creation of a user and login to ensure passwords are kept securely

/*links of sites used for this class
https://docs.oracle.com/javase/8/docs/api/java/util/Base64.html
https://www.geeksforgeeks.org/sha-256-hash-in-java/
https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html
https://docs.oracle.com/javase/8/docs/api/java/security/SecureRandom.html
*/

package utils;

//imported classes
import java.security.SecureRandom;
import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingUtils {

    /*generating a salt
   Using this method to generate a salt which is a random value added to the password before hashing password
   This is just to make sure that of two users have the same password they will have different hashes
    */
    public static String getSalt() {
        SecureRandom random = new SecureRandom();//creates random number generator that is suitable for cryptographic use
        byte[] salt = new byte[16];//byte array to hold the salt
        random.nextBytes(salt); // fills the salt array with random bytes generated by the SecureRandom instance
        return Base64.getEncoder().encodeToString(salt); //array is encoded into a Base64 string.
        // Base64 encoding is used to convert binary data -->  text format so that it can be stored/transmitted
    }

    /* Hashing Password
    hashPasswordWithSHA256() method takes a password and a salt
    returns the SHA-256 hash of the combination

    */
    public static String hashPasswordWithSHA256(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");//retrieves a MessageDigest instance that implements the SHA-256 hashing algorithm
            md.update(Base64.getDecoder().decode(salt));//salt (stored as a Base64 string) is first decoded back to its binary form and then used to update the digest
            byte[] bytes = md.digest(passwordToHash.getBytes());//actual password string is converted to bytes and hashed with the salt already added to the digest, returns the hashed byte array
            StringBuilder sb = new StringBuilder();// StringBuilder  used to convert the hashed byte array into a hexadecimal String format
            // Each byte is converted to hex using bitwise operations and padding to ensure two hex digits per byte.
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {//This is checked to handle cases where the hashing algorithm requested ("SHA-256") is not available
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
