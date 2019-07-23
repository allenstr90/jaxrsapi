package aem.example.jee.jaxrsapi.util;

import aem.example.jee.jaxrsapi.model.UserJWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JWTService {

    private static final Logger LOGGER = Logger.getLogger(JWTService.class.getName());
    private static final String SECRET = "secret";
    private static final String ISSUER = "jaxrsapi";
    private static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET);

    private JWTService() {
    }

    public static String generateToken(String username, String[] roles) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiredAt = new Date(issuedAt.getTime() + 1 * 3600 * 1000); // 1 hour

        return JWT.create()
                .withSubject(username)
                .withArrayClaim("roles", roles)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiredAt)
                .withIssuer(ISSUER)
                .withClaim("type", "accessToken")
                .sign(ALGORITHM);
    }

    public static String generateRefreshToken(String username) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiredAt = new Date(issuedAt.getTime() + 24 * 3600 * 1000); // 24 days

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiredAt)
                .withClaim("type", "refreshToken")
                .withIssuer(ISSUER)
                .sign(ALGORITHM);
    }

    public static boolean validateToken(String token) {
        LOGGER.info("Validating jwt token");
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM).withIssuer(ISSUER).withClaim("type", "accessToken").build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    public static UserJWT getUserJwt(String token) {
        DecodedJWT decoded = JWT.decode(token);
        return UserJWT.builder().username(decoded.getSubject())
                .roles(decoded.getClaim("roles").asArray(String.class)).build();
    }
}
