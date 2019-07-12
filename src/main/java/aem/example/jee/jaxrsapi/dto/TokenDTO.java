package aem.example.jee.jaxrsapi.dto;

import lombok.Builder;
import lombok.Data;

import javax.json.bind.annotation.JsonbNillable;
import java.io.Serializable;

@Data
@Builder
@JsonbNillable
public class TokenDTO implements Serializable {
    private final String version = "v1";
    private String token;
}
