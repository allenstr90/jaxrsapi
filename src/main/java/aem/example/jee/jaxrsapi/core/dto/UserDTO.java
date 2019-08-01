package aem.example.jee.jaxrsapi.core.dto;

import aem.example.jee.jaxrsapi.core.model.Role;
import aem.example.jee.jaxrsapi.core.model.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.config.PropertyOrderStrategy;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
public class UserDTO {

    private Long id;
    private String username;
    @Getter(AccessLevel.NONE)
    private String password;
    @JsonbProperty(nillable = true)
    private Set<String> roles;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    }

    @JsonbTransient
    public String getPassword() {
        return this.password;
    }
}
