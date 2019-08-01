package aem.example.jee.jaxrsapi.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "APP_GROUP")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "Group.findAll", query = "select g from Group g")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "APP_ROLES_GROUPS",
            joinColumns = {
                    @JoinColumn(name = "id_role", referencedColumnName = "id"),
                    @JoinColumn(name = "rolename", referencedColumnName = "name")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "id_group", referencedColumnName = "id"),
                    @JoinColumn(name = "groupname", referencedColumnName = "name")
            }
    )
    private Set<Role> roles;
}
