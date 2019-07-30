package aem.example.jee.jaxrsapi.core.repository;

import aem.example.jee.jaxrsapi.core.model.Role;
import aem.example.jee.jaxrsapi.core.model.User;
import aem.example.jee.jaxrsapi.core.model.User_;
import aem.example.jee.jaxrsapi.core.type.Pageable;
import aem.example.jee.jaxrsapi.core.type.UserSearchForm;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager em;

    @Override
    public Optional<User> findByUsername(String username) {
        return em
                .createNamedQuery(User.FIND_BY_USERNAME, User.class)
                .setParameter(User.FIND_BY_USERNAME_PARAM_USERNAME, username)
                .getResultList().stream().findFirst();
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
    public List<User> findByUserSearchForm(UserSearchForm searchForm, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<User> root = criteriaQuery.from(User.class);

        if (searchForm.getUsername() != null && !searchForm.getUsername().isEmpty()) {
            Predicate predicate = criteriaBuilder.like(root.get("username"), "%" + searchForm.getUsername() + "%");
            predicates.add(predicate);
        }

        if (searchForm.getInRole() != null && !searchForm.getInRole().isEmpty()) {
            Join<User, Role> userRoleJoin = root.join(User_.roles);
            Predicate predicate = criteriaBuilder.like(userRoleJoin.get("name"), "%" + searchForm.getInRole() + "%");
            predicates.add(predicate);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        List<Order> orders = new ArrayList<>();
        List<Pageable.Order> sort = pageable.getOrder();
        sort.forEach(sort1 -> {
            if (sort1.isAscending()) {
                orders.add(criteriaBuilder.asc(root.get(sort1.getProperty())));
            } else if (sort1.isDescending()) {
                orders.add(criteriaBuilder.desc(root.get(sort1.getProperty())));
            }
        });
        criteriaQuery.orderBy(orders);
        TypedQuery<User> query = em.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPage());
        query.setMaxResults(pageable.getSize());

        return query.getResultList();
    }
}