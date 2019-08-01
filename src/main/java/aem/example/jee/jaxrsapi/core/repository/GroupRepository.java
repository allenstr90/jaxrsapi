package aem.example.jee.jaxrsapi.core.repository;

import aem.example.jee.jaxrsapi.core.model.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.stream.Stream;

public interface GroupRepository {
    Optional<Group> findByName(String name);

    Stream<Group> findAll();

    class GroupRepositoryImpl implements GroupRepository {

        @PersistenceContext(unitName = "securityStorePU")
        private EntityManager em;

        @Override
        public Optional<Group> findByName(String name) {
            return Optional.empty();
        }

        @Override
        public Stream<Group> findAll() {
            return null;
        }
    }
}
