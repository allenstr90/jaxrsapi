package aem.example.jee.jaxrsapi.dto;

import aem.example.jee.jaxrsapi.model.Role;
import aem.example.jee.jaxrsapi.model.User;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private Set<String> roles;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    }
}
