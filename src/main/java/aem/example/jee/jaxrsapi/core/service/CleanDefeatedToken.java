package aem.example.jee.jaxrsapi.core.service;

import aem.example.jee.jaxrsapi.core.repository.TokenRefreshRepository;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.logging.Logger;

@Startup
@Singleton
public class CleanDefeatedToken {

    private static Logger logger = Logger.getLogger(CleanDefeatedToken.class.getName());

    @Inject
    private TokenRefreshRepository tokenRefreshRepository;

    @Schedule(hour = "*/5")
    public void cleanDefeatedTokens() {
        logger.info("Cleaning due tokens");
        tokenRefreshRepository.deleteDefeatedTokens();
    }
}
