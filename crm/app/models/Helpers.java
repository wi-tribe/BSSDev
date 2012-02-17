package models;

import java.util.regex.Pattern;

/**
 * Some helpers to simply things
 * @author stas
 */
public class Helpers {

    /**
     * Validates an email address
     * @param email String to validate
     * @return True on success, False on error
     */
    public static Boolean checkEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.matches(emailPattern, email);
    }
}
