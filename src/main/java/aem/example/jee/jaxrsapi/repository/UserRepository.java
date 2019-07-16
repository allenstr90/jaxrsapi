package aem.example.jee.jaxrsapi.repository;

import aem.example.jee.jaxrsapi.model.User;
import aem.example.jee.jaxrsapi.type.Pageable;
import aem.example.jee.jaxrsapi.type.UserSearchForm;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);

    User saveUser(User user);

    List<String> getUserRoles(String username);

    List<User> findByUserSearchForm(UserSearchForm searchForm, Pageable pageable);
}
