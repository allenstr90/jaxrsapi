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
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @Getter(AccessLevel.NONE)
    private Collection<User> users;

    @JsonbTransient
    public Collection<User> getUsers() {
        return this.users;
    }
}
