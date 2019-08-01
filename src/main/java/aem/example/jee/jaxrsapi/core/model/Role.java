package aem.example.jee.jaxrsapi.core.model;

import lombok.*;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "APP_ROLE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name = Role.FIND_BY_NAME,
        query = "select r from Role r where r.name= :" + Role.FIND_BY_NAME_PARAM_NAME
)
public class Role implements Serializable {

    public static final String FIND_BY_NAME = "Role.findByName";
    public static final String FIND_BY_NAME_PARAM_NAME = "name";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Getter(AccessLevel.NONE)
    private Collection<User> users;

    @ManyToMany(mappedBy = "roles")
    private Collection<Group> groups;

    @JsonbTransient
    public Collection<User> getUsers() {
        return this.users;
    }
}
