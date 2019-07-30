package aem.example.jee.jaxrsapi.core.util;

import aem.example.jee.jaxrsapi.core.type.Pageable;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PaginatorUtil {

    private PaginatorUtil() {
    }

    public static Pageable build(int page, int size, List<String> sort, Class aClass) {
        List<Pageable.Sort> sorts = sort
                .stream()
                .filter(o -> !o.isEmpty() && o.split(",").length <= 2)
                .filter(o -> {
                    try {
                        return aClass.getDeclaredField(o.split(",")[0]) != null
                                && !aClass.getDeclaredField(o.split(",")[0]).getType().isAssignableFrom(Collection.class);
                    } catch (NoSuchFieldException e) {
                        return false;
                    }
                })
                .map(o -> o.split(","))
                .map(strings -> {
                    String property = strings[0];
                    Pageable.Direction direction = Pageable.Direction.ASC;
                    if (strings[1] != null) {
                        direction = Arrays.stream(Pageable.Direction.values())
                                .parallel()
                                .filter(direction1 -> direction1.name().equalsIgnoreCase(strings[1]))
                                .findAny()
                                .orElse(Pageable.Direction.ASC);
                    }
                    return new Pageable.Sort(property, direction);
                })
                .collect(Collectors.toList());

        return Pageable.builder().page(page).size(size).sorts(sorts).build();
    }

    public static Pageable from(MultivaluedMap<String, String> map, Class aClass) {
        int page = map.containsKey("page") ? Integer.valueOf(map.getFirst("page")) : 0;
        int size = map.containsKey("size") ? Integer.valueOf(map.getFirst("size")) : 10;
        List<String> order = map.containsKey("sort") ? map.get("sort") : new ArrayList<>();
        return build(page, size, order, aClass);
    }
}
