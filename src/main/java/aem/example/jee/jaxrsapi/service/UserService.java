package aem.example.jee.jaxrsapi.service;

import aem.example.jee.jaxrsapi.dto.UserDTO;
import aem.example.jee.jaxrsapi.model.User;
import aem.example.jee.jaxrsapi.repository.RoleRepository;
import aem.example.jee.jaxrsapi.repository.UserRepository;
import aem.example.jee.jaxrsapi.type.Pageable;
import aem.example.jee.jaxrsapi.type.UserSearchForm;
import aem.example.jee.jaxrsapi.util.PasswordEncoder;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserService {
    Optional<UserDTO> findByUsername(String username);

    UserDTO saveUser(UserDTO user);

    List<String> getUserRoles(String username);

    List<UserDTO> findByUserSearchForm(UserSearchForm searchForm, Pageable pageable);


    class UserServiceImpl implements UserService {

        @Inject
        private UserRepository userRepository;

        @Inject
        private RoleRepository roleRepository;

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
        public List<UserDTO> findByUserSearchForm(UserSearchForm searchForm, Pageable pageable) {
            return userRepository.findByUserSearchForm(searchForm, pageable)
                    .stream().map(UserDTO::new).collect(Collectors.toList());
        }
    }
}
