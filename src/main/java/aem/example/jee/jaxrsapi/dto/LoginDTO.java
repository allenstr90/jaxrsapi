package aem.example.jee.jaxrsapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class LoginDTO implements Serializable {

    private String username;
    private String password;
}
