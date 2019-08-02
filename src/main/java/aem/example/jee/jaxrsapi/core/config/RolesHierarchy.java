package aem.example.jee.jaxrsapi.core.config;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Deprecated
public class RolesHierarchy {

    public static List<String> getSubRoles(Role role, List<String> roles) {
        roles.add(role.getName());
        if (role.getChildren().size() > 0) {
            for (Role r : role.getChildren()) {
                getSubRoles(r, roles);
            }
        }
        return roles;
    }

    public static Optional<Role> locateRole(String name, Role tree) {
        if (tree.getName().equalsIgnoreCase(name)) {
            return Optional.of(tree);
        }
        if (tree.getChildren().size() > 0)
            for (Role r : tree.getChildren()) {
                locateRole(name, r);
            }
        return Optional.empty();
    }

    @Getter
    @Setter
    private class Role {
        private String name;
        private Set<Role> children;

        public Role(String name) {
            this.name = name;
            this.children = new HashSet<>();
        }

        public void addChild(Role role) {
            this.children.add(role);
        }
    }
}
