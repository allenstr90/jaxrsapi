package aem.example.jee.jaxrsapi.core.model;

import aem.example.jee.jaxrsapi.core.converter.LocalDateToDatetimeConverter;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "USER_REFRESH_TOKENS")
@Data
@NamedQuery(
        name = "TokenRefresh.findByUsername",
        query = "select c from TokenRefresh  c where c.username =:username"
)
@NamedQuery(
        name = "TokenRefresh.deleteByUsername",
        query = "delete from TokenRefresh where username =:username"
)
@NamedQuery(
        name = "TokenRefresh.findAllByDateValid",
        query = "select t from TokenRefresh t where t.createDate =:username"
)
public class TokenRefresh implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(length = 1000, nullable = false)
    private String refreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Convert(converter = LocalDateToDatetimeConverter.class)
    private LocalDateTime createDate = LocalDateTime.now();
}
