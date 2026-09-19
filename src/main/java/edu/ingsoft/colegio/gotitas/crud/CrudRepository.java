
package main.java.edu.ingsoft.colegio.gotitas.crud;

import java.util.List;


public interface CrudRepository<T, ID> {

    void save(T entity) throws Exception;

    List<T> findAll() throws Exception;

    void update(T entity) throws Exception;

    void deleteById(ID id) throws Exception;
}