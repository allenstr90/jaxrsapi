package aem.example.jee.jaxrsapi.core.util;

import aem.example.jee.jaxrsapi.core.model.UserJWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class JWTService {

    private static final Logger LOGGER = Logger.getLogger(JWTService.class.getName());
    private static final String SECRET = "secret";
    private static final String ISSUER = "jaxrsapi";
    private static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET);

    public String generateToken(String username, String[] roles) {

        Instant now = Instant.now();
        Instant plus = now.plus(1, ChronoUnit.HOURS);

        return JWT.create()
                .withSubject(username)
                .withArrayClaim("roles", roles)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(plus))
                .withIssuer(ISSUER)
                .withClaim("type", "accessToken")
                .sign(ALGORITHM);
    }

    public String generateRefreshToken(String username) {

        Instant now = Instant.now();
        Instant plus = now.plus(1, ChronoUnit.DAYS);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(plus))
                .withClaim("type", "refreshToken")
                .withIssuer(ISSUER)
                .sign(ALGORITHM);
    }

    public boolean validateAccessToken(String token) {
        LOGGER.info("Validating jwt access token");
        return validateToken(token, "accessToken");
    }

    public boolean validateRefreshToken(String token) {
        LOGGER.info("Validating jwt refresh token");
        return validateToken(token, "refreshToken");
    }

    private boolean validateToken(String token, String claimType) {
        LOGGER.info("Validating jwt token");
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM).withIssuer(ISSUER)
                    .withClaim("type", claimType)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    public UserJWT getUserJwt(String token) {
        DecodedJWT decoded = JWT.decode(token);
        return UserJWT.builder().username(decoded.getSubject())
                .roles(decoded.getClaim("roles").asArray(String.class)).build();
    }
}
