package aem.example.jee.jaxrsapi.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USER_REFRESH_TOKENS")
@Data
@NamedQuery(
        name = "TokenRefresh.findByUsername",
        query = "select c from TokenRefresh  c where c.username =:username"
)
public class TokenRefresh implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(length = 1000, nullable = false)
    private String refreshToken;


    private Date createDate = new Date();
}
