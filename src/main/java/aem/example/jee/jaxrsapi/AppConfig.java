//package aem.example.jee.jaxrsapi;
//
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Named;
//import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
//import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
//import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
//import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
//
////@FormAuthenticationMechanismDefinition(
////        loginToContinue = @LoginToContinue(
////                loginPage = "/login.html",
////                errorPage = "/error.html"
////        )
////)
////@BasicAuthenticationMechanismDefinition(realmName = "realmBookStore")
//@ApplicationScoped
//public class AppConfig extends ResourceConfig {
//    public AppConfig() {
//        packages("aem.example.jee.jaxrsapi");
//        register(RolesAllowedDynamicFeature.class);
//    }
//}
