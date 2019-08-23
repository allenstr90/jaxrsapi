package aem.example.jee.jaxrsapi.core.interceptor;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * Prevent login retires for attack.
 */

@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface PreventAttack {
}
