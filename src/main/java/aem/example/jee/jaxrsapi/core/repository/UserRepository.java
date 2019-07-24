package aem.example.jee.jaxrsapi.core.repository;

import aem.example.jee.jaxrsapi.core.model.User;
import aem.example.jee.jaxrsapi.core.type.Pageable;
import aem.example.jee.jaxrsapi.core.type.UserSearchForm;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);

    User saveUser(User user);

    List<String> getUserRoles(String username);

    List<User> findByUserSearchForm(UserSearchForm searchForm, Pageable pageable);
}
