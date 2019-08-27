package aem.example.jee.jaxrsapi.core.repository;

import aem.example.jee.jaxrsapi.core.model.LockUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.logging.Logger;

public interface LockUserRepository {
    LockUser checkUser(String username);

    void unlock(String username);

    LockUser update(LockUser lockUser);

    Optional<LockUser> getLockUser(String username);

    void unlockUsers();

    class LockUserRepositoryImpl implements LockUserRepository {

        private static Logger logger = Logger.getLogger(LockUserRepository.class.getName());

        @PersistenceContext(unitName = "tokenStorePU")
        private EntityManager em;

        @Override
        @Transactional(Transactional.TxType.REQUIRED)
        public LockUser checkUser(String username) {
            LockUser lockUser = em.find(LockUser.class, username);
            if (lockUser == null) {
                lockUser = new LockUser();
                lockUser.setUsername(username);
                em.persist(lockUser);
            } else {
                lockUser.setTrays(lockUser.getTrays() + 1);
                lockUser = em.merge(lockUser);
                em.flush();
                logger.info(String.format("Username [%s] try login [%s] times", username, lockUser.getTrays()));
            }
            return lockUser;
        }

        @Override
        @Transactional(Transactional.TxType.REQUIRED)
        public void unlock(String username) {
            LockUser lockUser = em.find(LockUser.class, username);
            if (lockUser != null) {
                lockUser.setTo(null);
                lockUser.setTrays(0);
                lockUser.setLocked(false);
                em.merge(lockUser);
                em.flush();
            }
            logger.info(() -> "Username login ok and unlocked " + username);
        }

        @Override
        @Transactional(Transactional.TxType.REQUIRED)
        public LockUser update(LockUser lockUser) {
            return em.merge(lockUser);
        }

        @Override
        public Optional<LockUser> getLockUser(String username) {
            return Optional.ofNullable(em.find(LockUser.class, username));
        }

        @Override
        @Transactional(Transactional.TxType.REQUIRED)
        public void unlockUsers() {
            em.createNamedQuery("LockUser.unlockUsers", LockUser.class)
                    .setParameter("currentDate", Instant.now())
                    .executeUpdate();
        }
    }
}
