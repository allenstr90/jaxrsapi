package aem.example.jee.jaxrsapi.service;

import aem.example.jee.jaxrsapi.dto.TokenDTO;
import aem.example.jee.jaxrsapi.util.JWTService;

import java.util.Set;

public interface TokenService {
    TokenDTO generateCredentials(String username, Set<String> roles);

    class TokenServiceImpl implements TokenService {
        @Override
        public TokenDTO generateCredentials(String username, Set<String> roles) {
            String token = JWTService.generateToken(username, roles.toArray(new String[0]));
            String refreshToken = JWTService.generateRefreshToken(username);

            return TokenDTO.builder().token(token).refeshToken(refreshToken).build();
        }
    }
}
