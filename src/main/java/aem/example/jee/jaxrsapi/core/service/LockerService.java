package aem.example.jee.jaxrsapi.core.service;

import aem.example.jee.jaxrsapi.core.exception.UserLockException;
import aem.example.jee.jaxrsapi.core.model.LockUser;
import aem.example.jee.jaxrsapi.core.repository.LockUserRepository;

import javax.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class LockerService {
    @Inject
    private LockUserRepository lockUserRepository;

    public void unlock(String username) {
        lockUserRepository.unlock(username);
    }

    public void markToLock(String username) throws UserLockException {
        LockUser lockUser = lockUserRepository.checkUser(username);
        if (lockUser.getTrays() + 1 > 3) {
            lockUser.setLocked(true);
            lockUser.setTo(Instant.now().plus(20, ChronoUnit.SECONDS));
            lockUserRepository.update(lockUser);
            throw new UserLockException(username);
        }
    }
}
