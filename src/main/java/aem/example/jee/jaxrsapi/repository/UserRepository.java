package aem.example.jee.jaxrsapi.repository;

import aem.example.jee.jaxrsapi.type.Pageable;
import aem.example.jee.jaxrsapi.type.UserSearchForm;
import aem.example.jee.jaxrsapi.model.User;

import java.util.List;

public interface UserRepository {

    User findByUsername(String username);

    List<User> findAll();

    User saveUser(User user);

    List<String> getUserRoles(String username);

    List<User> findByUserSearchForm(UserSearchForm searchForm, Pageable pageable);
}
