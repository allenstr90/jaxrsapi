package aem.example.jee.jaxrsapi.core.service;

import aem.example.jee.jaxrsapi.core.exception.UserLockException;
import aem.example.jee.jaxrsapi.core.model.LockUser;
import aem.example.jee.jaxrsapi.core.model.User;
import aem.example.jee.jaxrsapi.core.repository.LockUserRepository;
import aem.example.jee.jaxrsapi.core.repository.TokenRefreshRepository;
import aem.example.jee.jaxrsapi.core.repository.UserRepository;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Stateless
public class LockerService {
    @Inject
    private LockUserRepository lockUserRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private TokenRefreshRepository tokenRefreshRepository;

    public void unlock(String username) {
        lockUserRepository.unlock(username);
    }

    public void markToLock(String username) throws UserLockException {
        LockUser lockUser = lockUserRepository.checkUser(username);
        if (lockUser.getTrays() == 3) {
            lockUser.setLocked(true);
            lockUser.setTo(Instant.now().plus(20, ChronoUnit.SECONDS));
            lockUserRepository.update(lockUser);
            tokenRefreshRepository.removeUserRefreshToken(username);
            throw new UserLockException(username);
        }
        if (lockUser.getTrays() + 1 > 5) {
            Optional<User> byUsernameAndActive = userRepository.findByUsernameAndActive(username);
            byUsernameAndActive.ifPresent(user -> {
                user.setActive(false);
                userRepository.updateUser(user);
            });
        }
    }

    public boolean userIsLocked(String username) {
        Optional<LockUser> lockUser = lockUserRepository.getLockUser(username);
        return lockUser.map(user -> (user.getTrays() + 1 > 3)).orElse(false);
    }

    @Schedule(minute = "*/10", hour = "*")
    public void unlockUsers() {
        lockUserRepository.unlockUsers();
    }
}
