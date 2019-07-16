package aem.example.jee.jaxrsapi.repository;

import aem.example.jee.jaxrsapi.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(String name);

    class RoleRepositoryImpl implements RoleRepository {

        @PersistenceContext(unitName = "bookStorePU")
        private EntityManager em;

        @Override
        public Optional<Role> findByName(String name) {
            return em.createNamedQuery(Role.FIND_BY_NAME, Role.class)
                    .setParameter(Role.FIND_BY_NAME_PARAM_NAME, name)
                    .getResultList().stream().findFirst();
        }
    }
}
