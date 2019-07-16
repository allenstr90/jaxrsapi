package aem.example.jee.jaxrsapi.dto;

import lombok.Builder;
import lombok.Data;

import javax.json.bind.annotation.JsonbNillable;
import java.io.Serializable;

@Data
@Builder
@JsonbNillable
public class TokenDTO implements Serializable {
    private static final String VERSION = "v1";
    private String token;
}
