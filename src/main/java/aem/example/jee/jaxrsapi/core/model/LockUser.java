package aem.example.jee.jaxrsapi.core.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "LOCKED_USER")
@Data
public class LockUser {
    @Id
    private String username;
    private Instant to;
    private boolean locked = false;
    private int trays = 0;
}
