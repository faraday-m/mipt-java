package edu.phystech;

public class SimpleKeyExtractor implements KeyExtractor<Long, BankEntity> {
    @Override
    public Long extract(BankEntity entity) {
        return entity.getId();
    }
}
