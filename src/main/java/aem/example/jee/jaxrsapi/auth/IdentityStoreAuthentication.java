//package aem.example.jee.jaxrsapi.auth;
//
//import aem.example.jee.jaxrsapi.model.User;
//import aem.example.jee.jaxrsapi.repository.UserRepository;
//import aem.example.jee.jaxrsapi.util.PasswordEncoder;
//import java.security.NoSuchAlgorithmException;
//import java.util.EnumSet;
//import java.util.Set;
//import java.util.logging.Logger;
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import javax.security.enterprise.credential.UsernamePasswordCredential;
//import javax.security.enterprise.identitystore.CredentialValidationResult;
//
//@ApplicationScoped
//public class IdentityStoreAuthentication implements javax.security.enterprise.identitystore.IdentityStore {
//
//    private static final Logger logger = Logger.getLogger(IdentityStoreAuthentication.class.getName());
//
//    @Inject
//    UserRepository userRepository;
//
//    public CredentialValidationResult validate(UsernamePasswordCredential credential) throws NoSuchAlgorithmException {
//
//        String username = credential.getCaller();
//        logger.info(String.format("Authenticating user %s", username));
//
//        User user = userRepository.findByUsername(username);
//        if (user != null) {
//            String password = credential.getPasswordAsString();
//            String encodedPassword = PasswordEncoder.encodeSha256(password);
//            if (encodedPassword.equals(user.getPassword())) {
//                return new CredentialValidationResult(username);
//            }
//        }
//        return CredentialValidationResult.INVALID_RESULT;
//    }
//
//    @Override
//    public int priority() {
//        return 70;
//    }
//
//    @Override
//    public Set<ValidationType> validationTypes() {
//        return EnumSet.of(ValidationType.VALIDATE);
//    }
//}
