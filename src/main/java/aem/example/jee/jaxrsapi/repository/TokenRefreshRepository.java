package aem.example.jee.jaxrsapi.repository;

import aem.example.jee.jaxrsapi.model.TokenRefresh;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.stream.Stream;

public interface TokenRefreshRepository {

    TokenRefresh save(TokenRefresh tokenRefresh);

    Stream<TokenRefresh> findByUsername(String username);

    class TokenRefreshRepositoryImpl implements TokenRefreshRepository {

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
    }
}
