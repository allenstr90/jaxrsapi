///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package aem.example.jee.jaxrsapi.auth;
//
//import aem.example.jee.jaxrsapi.model.Role;
//import aem.example.jee.jaxrsapi.model.User;
//import aem.example.jee.jaxrsapi.repository.UserRepository;
//import java.util.Collections;
//import java.util.EnumSet;
//import java.util.Set;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import javax.security.enterprise.identitystore.CredentialValidationResult;
//import javax.security.enterprise.identitystore.IdentityStore;
//
//@ApplicationScoped
//public class IdentityStoreAuthorization implements IdentityStore {
//
//    private static Logger logger = Logger.getLogger(IdentityStoreAuthorization.class.getName());
//
//    @Inject
//    UserRepository userRepository;
//
//    @Override
//    public int priority() {
//        return 50;
//    }
//
//    @Override
//    public Set<ValidationType> validationTypes() {
//        return EnumSet.of(ValidationType.PROVIDE_GROUPS);
//    }
//
//    @Override
//    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
//        String username = validationResult.getCallerPrincipal().getName();
//        logger.info(String.format("Get roles for %s", username));
//        User user = userRepository.findByUsername(username);
//        if (user != null) {
//            logger.info(String.format("Found roles %s", user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", "))));
//            return user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet());
//        }
//        return Collections.EMPTY_SET;
//    }
//}
