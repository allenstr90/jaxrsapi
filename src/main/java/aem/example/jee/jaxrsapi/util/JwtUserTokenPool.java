package aem.example.jee.jaxrsapi.util;

import aem.example.jee.jaxrsapi.model.UserJWT;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class JwtUserTokenPool {
    private final Map<String, UserJWT> map = Collections.synchronizedMap(new HashMap<>());

    //todo add registry for user sessions

    public void register(UserJWT userJWT) {
        if (!map.containsKey(userJWT.getUsername()))
            map.put(userJWT.getUsername(), userJWT);
    }

    public void unregister(String username) {
        map.remove(username);
    }
}
