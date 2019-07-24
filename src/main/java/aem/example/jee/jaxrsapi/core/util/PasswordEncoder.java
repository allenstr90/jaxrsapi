package aem.example.jee.jaxrsapi.core.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PasswordEncoder {

    private PasswordEncoder() {
    }

    public static String encodeSha256(String password) {
        try {
            return generateSha256(password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordEncoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private static String generateSha256(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(hash);
    }
}
