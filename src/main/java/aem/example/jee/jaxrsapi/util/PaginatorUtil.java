package aem.example.jee.jaxrsapi.util;

import aem.example.jee.jaxrsapi.type.Pageable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PaginatorUtil {

    private PaginatorUtil() {
    }

    public static Pageable build(int page, int size, List<String> order, Class aClass) {
        List<Pageable.Order> orders = order
                .stream()
                .filter(o -> !o.isEmpty())
                .filter(o -> o.split(",").length <= 2)
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
                    Pageable.Sort sort = Pageable.Sort.ASC;
                    if (strings[1] != null) {
                        sort = Arrays.stream(Pageable.Sort.values())
                                .parallel()
                                .filter(sort1 -> sort1.name().equalsIgnoreCase(strings[1]))
                                .findAny()
                                .orElse(Pageable.Sort.ASC);
                    }
                    return new Pageable.Order(property, sort);
                })
                .collect(Collectors.toList());

        return Pageable.builder().page(page).size(size).order(orders).build();
    }
}
