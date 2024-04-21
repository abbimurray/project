//Student number:C00260073, Student name: Abigail Murray, Semester two

package utils;

import java.util.regex.Matcher;/*for email validation using regex*/
import java.util.regex.Pattern;

public class ValidationUtils {

    // Email validation method
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Password validation method - has a letter, a digit, special character and is at least 8 chars long
    public static boolean isValidPassword(String password) {
        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = password.matches(".*[^a-zA-Z0-9].*");

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }

            if(hasLetter && hasDigit && hasSpecialChar) {
                break;
            }
        }

        return password.length() >= 8 && hasLetter && hasDigit && hasSpecialChar;
    }//end isValidPassword


}//end class