package aem.example.jee.jaxrsapi.service;

import aem.example.jee.jaxrsapi.dto.TokenDTO;
import aem.example.jee.jaxrsapi.model.TokenRefresh;
import aem.example.jee.jaxrsapi.repository.TokenRefreshRepository;
import aem.example.jee.jaxrsapi.util.JWTService;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Stream;

public interface TokenService {
    TokenDTO generateCredentials(String username, Set<String> roles);

    boolean canRefreshToken(String username, String refreshToken);

    class TokenServiceImpl implements TokenService {
        @Inject
        private TokenRefreshRepository tokenRefreshRepository;

        @Override
        public TokenDTO generateCredentials(String username, Set<String> roles) {
            String token = JWTService.generateToken(username, roles.toArray(new String[0]));
            String refreshToken = JWTService.generateRefreshToken(username);
            TokenRefresh tokenRefresh = new TokenRefresh();
            tokenRefresh.setUsername(username);
            tokenRefresh.setRefreshToken(refreshToken);
            tokenRefreshRepository.save(tokenRefresh);
            return TokenDTO.builder().token(token).refeshToken(refreshToken).build();
        }

        @Override
        public boolean canRefreshToken(String username, String refreshToken) {
            Stream<TokenRefresh> byUsername = tokenRefreshRepository.findByUsername(username);
            return byUsername
                    .anyMatch(tokenRefresh -> tokenRefresh.getRefreshToken().equals(refreshToken));
        }
    }
}
