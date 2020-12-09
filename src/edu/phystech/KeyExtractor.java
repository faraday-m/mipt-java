package edu.phystech;

public interface KeyExtractor<K, V extends BankEntity> {
    K extract(V entity);
}
