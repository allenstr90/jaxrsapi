package aem.example.jee.jaxrsapi.core.interceptor;

import aem.example.jee.jaxrsapi.core.dto.UserDTO;
import aem.example.jee.jaxrsapi.core.service.AuthService;

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

    @AroundInvoke
    public Object checkUserLoginRetries(InvocationContext context) throws Exception {
        Object[] parameters = context.getParameters();
        String username = (parameters != null && parameters[0] != null) ? (String) parameters[0] : "";
        Object proceed = context.proceed();
        if (proceed != null) {
            if (proceed instanceof Optional) {
                Optional<UserDTO> result = (Optional<UserDTO>) proceed;
                authService.traceUserToLock(username, result.isPresent());
            }
        }
        return proceed;
    }
}