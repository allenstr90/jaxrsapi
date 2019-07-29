package aem.example.jee.jaxrsapi.core.service;

import aem.example.jee.jaxrsapi.core.dto.TokenDTO;
import aem.example.jee.jaxrsapi.core.model.TokenRefresh;
import aem.example.jee.jaxrsapi.core.repository.TokenRefreshRepository;
import aem.example.jee.jaxrsapi.core.util.JWTService;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Stream;

public interface TokenService {
    TokenDTO generateCredentials(String username, Set<String> roles);

    boolean canRefreshToken(String username, String refreshToken);

    class TokenServiceImpl implements TokenService {
        @Inject
        private TokenRefreshRepository tokenRefreshRepository;

        @Inject
        private JWTService jwtService;

        @Override
        public TokenDTO generateCredentials(String username, Set<String> roles) {
            String token = jwtService.generateToken(username, roles.toArray(new String[0]));
            String refreshToken = jwtService.generateRefreshToken(username);
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
                    .parallel()
                    .filter(tokenRefresh -> tokenRefresh.getRefreshToken().equals(refreshToken))
                    .peek(tokenRefresh -> tokenRefreshRepository.delete(username))
                    .findAny().isPresent();
        }
    }
}
