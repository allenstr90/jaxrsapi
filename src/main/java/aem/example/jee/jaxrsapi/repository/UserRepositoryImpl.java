package aem.example.jee.jaxrsapi.repository;

import aem.example.jee.jaxrsapi.model.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext(unitName = "bookStorePU")
    EntityManager em;

    @Override
    public User findByUsername(String username) {
        return em
                .createNamedQuery(User.FIND_BY_USERNAME, User.class)
                .setParameter(User.FIND_BY_USERNAME_PARAM_USERNAME, username)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return em.createNamedQuery(User.FIND_ALL, User.class).getResultList();
    }


    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public User saveUser(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public List<String> getUserRoles(String username) {
        return em.createNamedQuery(User.FIND_USER_ROLES, String.class)
                .setParameter(1, username)
                .getResultList();
    }
}
