package aem.example.jee.jaxrsapi.core.model;

import lombok.*;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "APP_USER")
@Cacheable(false)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = User.FIND_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username=:" + User.FIND_BY_USERNAME_PARAM_USERNAME)
@NamedQuery(name = User.FIND_BY_USERNAME_ACTIVE, query = "SELECT u FROM User u WHERE u.active = true AND u.username=:" + User.FIND_BY_USERNAME_PARAM_USERNAME)
@NamedQuery(name = User.FIND_ALL, query = "SELECT u FROM User u")
@NamedNativeQuery(name = User.FIND_USER_ROLES, query = "select ROLENAME from APP_USERS_ROLES where USERNAME =?1")
public class User implements Serializable {

    public static final String FIND_BY_USERNAME = "User.findByUsername";
    public static final String FIND_BY_USERNAME_ACTIVE = "User.findByUsernameAndActive";
    public static final String FIND_BY_USERNAME_PARAM_USERNAME = "username";
    public static final String FIND_ALL = "User.findAll";
    public static final String FIND_USER_ROLES = "User.findUserRoles";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Getter(AccessLevel.NONE)
    private String password;

    private boolean active = true;

    @JoinTable(name = "APP_USERS_ROLES",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id"),
                    @JoinColumn(name = "username", referencedColumnName = "username")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id"),
                    @JoinColumn(name = "rolename", referencedColumnName = "name")
            })
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @JsonbTransient
    public String getPassword() {
        return password;
    }
}
