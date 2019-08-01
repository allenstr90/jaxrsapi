package aem.example.jee.jaxrsapi.core.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Pageable<T> {

    private final Class<T> entity;
    @Builder.Default
    @JsonbProperty(nillable = true)
    private List<Sort> sorts = new ArrayList<>();
    @Builder.Default
    @JsonbProperty(nillable = true)
    private int page = 0;
    @Builder.Default
    @JsonbProperty(nillable = true)
    private int size = 10;

    public Pageable(Class<T> entity) {
        this.entity = entity;
    }

    public enum Direction {
        ASC, DESC
    }

    @Data
    @AllArgsConstructor
    public static class Sort {
        private String property;
        private Direction direction;

        public boolean isAscending() {
            return direction == Direction.ASC;
        }

        public boolean isDescending() {
            return direction == Direction.DESC;
        }
    }
}
