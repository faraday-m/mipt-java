package edu.phystech;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleEntitiesStorage<K,V extends BankEntity> implements BankEntitiesStorage<K, V> {
    private final Map<K, V> storage = new HashMap<>();
    private final KeyExtractor<K, ? super V> keyExtractor;

    public SimpleEntitiesStorage(KeyExtractor<K, ? super V> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }


    @Override
    public void save(V entity) {
        if (entity == null) {
            throw new NullPointerException();
        }
        K key = keyExtractor.extract(entity);
        storage.put(key, entity);
    }

    @Override
    public void saveAll(List<? extends V> entities) {
        entities.forEach(this::save);
    }

    @Override
    public V findByKey(K key) {
        return storage.get(key);
    }

    @Override
    public List<V> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteByKey(K key) {
        storage.remove(key);
    }

    @Override
    public void deleteAll(List<? extends V> entities) {
        entities.stream().map(keyExtractor::extract).forEach(this::deleteByKey);
    }
}
