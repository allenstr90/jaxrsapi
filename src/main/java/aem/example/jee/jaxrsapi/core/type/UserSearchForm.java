package aem.example.jee.jaxrsapi.core.type;

import lombok.Builder;
import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;

@Data
@Builder
public class UserSearchForm {
    @JsonbProperty(nillable = true)
    private String username;
    @JsonbProperty(nillable = true)
    private String inRole;
}
