package aem.example.jee.jaxrsapi.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserJWT {
    private String username;
    private String[] roles;
}
