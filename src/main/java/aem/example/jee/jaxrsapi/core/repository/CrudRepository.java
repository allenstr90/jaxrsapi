package aem.example.jee.jaxrsapi.core.repository;

public interface CrudRepository<T> {

    T findById(Long id);
}
