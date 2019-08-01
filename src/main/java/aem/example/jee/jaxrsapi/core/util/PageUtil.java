package aem.example.jee.jaxrsapi.core.util;

import aem.example.jee.jaxrsapi.core.type.Page;

import java.util.List;

public class PageUtil {

    private PageUtil() {
    }

    public static Page from(long total, int page, int size, List data) {
        return new Page() {
            @Override
            public long getTotal() {
                return total;
            }

            @Override
            public int getPage() {
                return page;
            }

            @Override
            public int getSize() {
                return size;
            }

            @Override
            public List getData() {
                return data;
            }
        };
    }
}
