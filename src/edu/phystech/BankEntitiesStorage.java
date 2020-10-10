package edu.phystech;

import java.util.Collection;
import java.util.List;

public interface BankEntitiesStorage<T> {
    void save(T entity);

    void saveAll(Collection<T> entities);

    List<T> findByKey(Object key);

    List<T> findAll();

    void deleteByKey(Object key);

    void deleteAll(List<T> entities);
}

