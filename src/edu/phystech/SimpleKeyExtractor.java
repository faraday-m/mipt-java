package edu.phystech;

public class SimpleKeyExtractor implements KeyExtractor {
    public Object extract(Object entity) {
        return entity.hashCode();
    }
}
