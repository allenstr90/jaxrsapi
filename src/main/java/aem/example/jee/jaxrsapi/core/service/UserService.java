package aem.example.jee.jaxrsapi.core.service;

import aem.example.jee.jaxrsapi.core.dto.UserDTO;
import aem.example.jee.jaxrsapi.core.factory.UserFactory;
import aem.example.jee.jaxrsapi.core.model.User;
import aem.example.jee.jaxrsapi.core.repository.RoleRepository;
import aem.example.jee.jaxrsapi.core.repository.UserRepository;
import aem.example.jee.jaxrsapi.core.type.Page;
import aem.example.jee.jaxrsapi.core.type.Pageable;
import aem.example.jee.jaxrsapi.core.type.UserSearchForm;
import aem.example.jee.jaxrsapi.core.util.PasswordEncoder;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserService {
    Optional<UserDTO> findByUsername(String username);

    UserDTO saveUser(UserDTO user);

    List<String> getUserRoles(String username);

    Page<UserDTO> findPageByUserSearchForm(UserSearchForm searchForm, Pageable pageable);


    class UserServiceImpl implements UserService {

        @Inject
        private UserRepository userRepository;

        @Inject
        private RoleRepository roleRepository;

        @Inject
        private UserFactory userFactory;

        @Override
        public Optional<UserDTO> findByUsername(String username) {
            return userRepository.findByUsername(username).map(UserDTO::new);
        }

        @Override
        public UserDTO saveUser(UserDTO user) {
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(PasswordEncoder.encodeSha256(user.getPassword()));
            newUser.setRoles(
                    user.getRoles()
                            .stream()
                            .map(roleRepository::findByName)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toList())
            );
            return new UserDTO(userRepository.saveUser(newUser));
        }

        @Override
        public List<String> getUserRoles(String username) {
            return userRepository.getUserRoles(username);
        }

        @Override
        public Page<UserDTO> findPageByUserSearchForm(UserSearchForm searchForm, Pageable pageable) {
            Page<User> pageByUserSearchForm = userRepository.findPageByUserSearchForm(searchForm, pageable);
            return userFactory.from(pageByUserSearchForm);
        }
    }
}
