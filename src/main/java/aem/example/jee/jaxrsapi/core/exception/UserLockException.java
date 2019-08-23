package aem.example.jee.jaxrsapi.core.exception;

public class UserLockException extends Exception {
    public UserLockException(String username) {
        super(String.format("The username [%s] has been temporally locked", username));
    }
}
