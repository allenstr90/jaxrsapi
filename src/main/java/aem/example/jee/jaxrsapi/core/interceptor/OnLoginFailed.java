package aem.example.jee.jaxrsapi.core.interceptor;

import aem.example.jee.jaxrsapi.core.dto.UserDTO;
import aem.example.jee.jaxrsapi.core.exception.UserLockException;
import aem.example.jee.jaxrsapi.core.service.AuthService;
import aem.example.jee.jaxrsapi.core.service.LockerService;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.Optional;
import java.util.logging.Logger;

@Interceptor
@PreventAttack
public class OnLoginFailed implements Serializable {
    private static Logger log = Logger.getLogger(OnLoginFailed.class.getName());

    @Inject
    private AuthService authService;

    @Inject
    private LockerService lockerService;

    @AroundInvoke
    @SuppressWarnings({"unchecked"})
    public Object checkUserLoginRetries(InvocationContext context) throws Exception {
        Object[] parameters = context.getParameters();
        String username = (parameters != null && parameters[0] != null) ? (String) parameters[0] : "";
        if (lockerService.userIsLocked(username)) {
            log.warning(() -> String.format("Username [%s] try login and is locked", username));
            throw new UserLockException(username);
        }
        Object proceed = context.proceed();
        if (proceed instanceof Optional) {
            Optional<UserDTO> result = (Optional<UserDTO>) proceed;
            authService.traceUserToLock(username, result.isPresent());
        }

        return proceed;
    }
}
