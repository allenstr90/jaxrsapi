package aem.example.jee.jaxrsapi.service;

import aem.example.jee.jaxrsapi.model.User;
import aem.example.jee.jaxrsapi.repository.UserRepository;
import aem.example.jee.jaxrsapi.util.PasswordEncoder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Optional;

@RequestScoped
public class AuthService {

    @Inject
    private UserRepository userRepository;

    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> optional = userRepository.findByUsername(username);
        return optional.filter(user -> PasswordEncoder.encodeSha256(password).equals(user.getPassword()));
    }
}
