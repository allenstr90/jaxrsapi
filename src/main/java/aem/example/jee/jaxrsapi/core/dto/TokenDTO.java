package aem.example.jee.jaxrsapi.core.dto;

import lombok.Builder;
import lombok.Data;

import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.config.PropertyOrderStrategy;
import java.io.Serializable;

@Data
@Builder
@JsonbNillable
@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
public class TokenDTO implements Serializable {
    private static final String VERSION = "v1";
    private String token;
    private String refreshToken;
}
