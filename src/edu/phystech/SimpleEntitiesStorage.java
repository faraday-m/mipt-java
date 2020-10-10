package edu.phystech;

import java.util.*;

public class SimpleEntitiesStorage<T extends BankEntity> implements BankEntitiesStorage<T> {
    private final Map<Object, List<T>> storage = new HashMap<>();
    private final KeyExtractor keyExtractor;

    public SimpleEntitiesStorage(KeyExtractor keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    @Override
    public void save(T entity) {
        if (storage.containsKey(keyExtractor.extract(entity))) {
            if (entity instanceof Transaction) {
                storage.get(keyExtractor.extract(entity)).removeIf(t -> (((Transaction) t).getId() == ((Transaction) entity).getId()));
            }
            storage.get(keyExtractor.extract(entity)).add(entity);
        } else {
            ArrayList<T> list = new ArrayList<>();
            list.add(entity);
            storage.put(keyExtractor.extract(entity), list);
        }
    }

    @Override
    public void saveAll(Collection<T> entities) {
        for (T t : entities) {
            save(t);
        }
    }

    @Override
    public List<T> findByKey(Object key) {
       if (storage.get(key) != null) {
            return storage.get(key);
       }
       return null;
    }

    @Override
    public List<T> findAll() {
        return combineLists(storage.values());
    }

    private List<T> combineLists(Collection<List<T>> args) {
        List<T> combinedList = new ArrayList<>();
        for (List<T> arg : args) {
            combinedList.addAll(arg);
        }
        return combinedList;
    }

    @Override
    public void deleteByKey(Object key) {
        storage.remove(key);
    }

    @Override
    public void deleteAll(List<T> entities) {
        for (T entity : entities) {
            Object key = keyExtractor.extract(entity);
            List<T> keys = new ArrayList<>(storage.get(key));
            for (T ent2 : keys) {
                if (entity.equals(ent2)) {
                    storage.get(key).remove(entity);
                }
            }
        }

    }
}
