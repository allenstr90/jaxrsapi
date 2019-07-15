package aem.example.jee.jaxrsapi.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

@Data
@Builder
public class Pageable {
    @JsonbProperty(nillable = true)
    private List<Order> order;
    @JsonbProperty(nillable = true)
    private int page;
    @JsonbProperty(nillable = true)
    private int size;

    public enum Sort {
        ASC, DESC
    }

    @Data
    @AllArgsConstructor
    public static class Order {
        private String property;
        private Sort direction;

        public boolean isAscending() {
            return direction == Sort.ASC;
        }
    }
}
