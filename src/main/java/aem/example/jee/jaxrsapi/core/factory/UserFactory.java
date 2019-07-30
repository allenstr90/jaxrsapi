package aem.example.jee.jaxrsapi.core.factory;

import aem.example.jee.jaxrsapi.core.dto.UserDTO;
import aem.example.jee.jaxrsapi.core.model.User;
import aem.example.jee.jaxrsapi.core.type.Page;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserFactory {

    public Page<UserDTO> from(Page<User> userPage) {
        return new Page<UserDTO>() {
            @Override
            public long getTotal() {
                return userPage.getTotal();
            }

            @Override
            public int getPage() {
                return userPage.getPage();
            }

            @Override
            public int getSize() {
                return userPage.getSize();
            }

            @Override
            public List<UserDTO> getData() {
                return userPage.getData().stream().parallel().map(UserDTO::new).collect(Collectors.toList());
            }
        };
    }
}
