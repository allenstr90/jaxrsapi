package aem.example.jee.jaxrsapi.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "book_store_role")
@Data
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

    @JsonbTransient
    public Collection<User> getUsers() {
        return this.users;
    }
}
