package aem.example.jee.jaxrsapi.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;


    @ManyToMany(mappedBy = "authors")
    @Getter(AccessLevel.NONE)
    private Set<Book> books;

    @JsonbTransient
    private Set<Book> getBooks() {
        return this.books;
    }
}
