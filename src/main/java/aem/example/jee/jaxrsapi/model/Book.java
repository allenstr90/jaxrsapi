package aem.example.jee.jaxrsapi.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NamedQueries({
        @NamedQuery(
                name = "Book.findAll",
                query = "select b from Book b"
        )
})
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    private String isbn;

    @JoinTable(
            name = "book_authors",
            joinColumns = {
                    @JoinColumn(name = "book_id", referencedColumnName = "id"),
                    @JoinColumn(name = "title", referencedColumnName = "title")},
            inverseJoinColumns = {
                    @JoinColumn(name = "auhtor_id", referencedColumnName = "id"),
                    @JoinColumn(name = "author", referencedColumnName = "name")}
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Author> authors;
}
