package aem.example.jee.jaxrsapi.core.repository;

import aem.example.jee.jaxrsapi.core.model.TokenRefresh;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public interface TokenRefreshRepository {

    TokenRefresh save(TokenRefresh tokenRefresh);

    Stream<TokenRefresh> findByUsername(String username);

    void removeUserRefreshToken(String username);

    void deleteDefeatedTokens();

    class TokenRefreshRepositoryImpl implements TokenRefreshRepository {
        private static Logger logger = Logger.getLogger(TokenRefreshRepository.class.getName());

        @PersistenceContext(unitName = "tokenStorePU")
        private EntityManager em;


        @Override
        @Transactional(Transactional.TxType.REQUIRED)
        public TokenRefresh save(TokenRefresh tokenRefresh) {
            em.persist(tokenRefresh);
            return tokenRefresh;
        }

        @Override
        public Stream<TokenRefresh> findByUsername(String username) {
            return
                    em.createNamedQuery("TokenRefresh.findByUsername", TokenRefresh.class)
                            .setParameter("username", username).getResultStream();
        }

        @Override
        @Transactional(Transactional.TxType.REQUIRED)
        public void removeUserRefreshToken(String username) {
            logger.log(Level.FINE, "Cleaning tokens for user {0}", username);
            em.createNamedQuery("TokenRefresh.deleteByUsername", TokenRefresh.class)
                    .setParameter("username", username).executeUpdate();

        }

        @Override
        @Transactional(Transactional.TxType.REQUIRED)
        public void deleteDefeatedTokens() {
            em.createNamedQuery("TokenRefresh.deleteDefeatedTokens", TokenRefresh.class)
                    .setParameter("defeatedDate", LocalDateTime.now().minusDays(1))
                    .executeUpdate();
        }
    }
}
