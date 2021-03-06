package aem.example.jee.jaxrsapi.core.service;

import aem.example.jee.jaxrsapi.core.dto.TokenDTO;
import aem.example.jee.jaxrsapi.core.dto.UserDTO;
import aem.example.jee.jaxrsapi.core.exception.UserLockException;
import aem.example.jee.jaxrsapi.core.interceptor.PreventAttack;
import aem.example.jee.jaxrsapi.core.model.User;
import aem.example.jee.jaxrsapi.core.model.UserJWT;
import aem.example.jee.jaxrsapi.core.repository.UserRepository;
import aem.example.jee.jaxrsapi.core.util.JWTService;
import aem.example.jee.jaxrsapi.core.util.PasswordEncoder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

@RequestScoped
public class AuthService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private TokenService tokenService;

    @Inject
    private JWTService jwtService;

    @Inject
    private LockerService lockerService;

    @PreventAttack
    public Optional<UserDTO> authenticateUser(String username, String password) {
        Optional<User> optional = userRepository.findByUsernameAndActive(username);
        return optional
                .filter(user -> PasswordEncoder.encodeSha256(password != null ? password : "").equals(user.getPassword()))
                .map(UserDTO::new);
    }

    public TokenDTO generateCredentials(String username, Set<String> roles) {
        return tokenService.generateCredentials(username, roles);
    }

    public Optional<TokenDTO> refreshToken(String refreshToken) {
        if (jwtService.validateRefreshToken(refreshToken)) {
            UserJWT userJwt = jwtService.getUserJwt(refreshToken);
            if (tokenService.canRefreshToken(userJwt.getUsername(), refreshToken)) {
                return generateUserDto(userJwt.getUsername())
                        .map(userDTO -> tokenService.generateCredentials(userDTO.getUsername(), userDTO.getRoles()));
            }
        }
        return Optional.empty();
    }

    private Optional<UserDTO> generateUserDto(String username) {
        Optional<User> optional = userRepository.findByUsername(username);
        return optional.map(UserDTO::new);
    }

    public void traceUserToLock(String username, boolean result) throws UserLockException {
        if (result) {
            lockerService.unlock(username);
        } else {
            lockerService.markToLock(username);
        }
    }
}
