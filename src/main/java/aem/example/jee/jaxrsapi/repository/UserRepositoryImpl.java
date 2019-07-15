package aem.example.jee.jaxrsapi.repository;

import aem.example.jee.jaxrsapi.dto.UserSearchForm;
import aem.example.jee.jaxrsapi.model.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @Override
    public List<User> findByUserSearchForm(UserSearchForm searchForm) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<User> root = criteriaQuery.from(User.class);
        if (searchForm.getUsername() != null && !searchForm.getUsername().isEmpty()) {
            Predicate predicate = criteriaBuilder.like(root.get("username"), "%" + searchForm.getUsername() + "%");
            predicates.add(predicate);
        }
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<User> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }
}