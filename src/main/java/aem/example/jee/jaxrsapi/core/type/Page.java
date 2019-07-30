package aem.example.jee.jaxrsapi.core.type;

import java.util.List;

public interface Page<T> {
    long getTotal();

    int getPage();

    int getSize();

    List<T> getData();
}
