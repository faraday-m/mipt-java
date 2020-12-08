package edu.phystech;

import java.util.Collection;
import java.util.List;

public interface BankEntitiesStorage<K, V> {
    void save(V entity);
    void saveAll(List<? extends V> entities);
    V findByKey(K key);
    List<V> findAll();
    void deleteByKey(K key);
    void deleteAll(List<? extends V> entities);
}


