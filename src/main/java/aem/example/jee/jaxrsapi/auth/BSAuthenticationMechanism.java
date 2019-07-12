//package aem.example.jee.jaxrsapi.auth;
//
//import java.security.NoSuchAlgorithmException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.inject.Alternative;
//import javax.enterprise.inject.Default;
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.inject.Qualifier;
//import javax.security.enterprise.AuthenticationException;
//import javax.security.enterprise.AuthenticationStatus;
//import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
//import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
//import javax.security.enterprise.credential.Password;
//import javax.security.enterprise.credential.UsernamePasswordCredential;
//import javax.security.enterprise.identitystore.CredentialValidationResult;
//import javax.security.enterprise.identitystore.IdentityStoreHandler;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@ApplicationScoped
//public class BSAuthenticationMechanism implements HttpAuthenticationMechanism {
//
//    @Inject
//    private IdentityStoreHandler identityStore;
//
//    @Override
//    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
//        //      try {
//
//        String caller = request.getParameter("j_username");
//        String password = request.getParameter("j_password");
//        if (caller != null && password != null) {
//            Password p = new Password(password);
//            CredentialValidationResult result = identityStore.validate(new UsernamePasswordCredential(caller, p));
//            if (result.getStatus() == CredentialValidationResult.Status.INVALID) {
//                return httpMessageContext.notifyContainerAboutLogin(result);
//            }
//            return httpMessageContext.responseUnauthorized();
//        }
//
////        } catch (NoSuchAlgorithmException ex) {
////            Logger.getLogger(BSAuthenticationMechanism.class.getName()).log(Level.SEVERE, null, ex);
////        }
//        return httpMessageContext.doNothing();
//    }
//
//}
