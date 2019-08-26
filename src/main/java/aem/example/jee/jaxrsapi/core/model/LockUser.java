package aem.example.jee.jaxrsapi.core.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "LOCKED_USER")
@NamedQuery(
        name = "LockUser.unlockUsers",
        query = "delete from LockUser where to <:currentDate"
)
@Data
public class LockUser {
    @Id
    private String username;
    private Instant to;
    private boolean locked = false;
    private int trays = 0;
}
