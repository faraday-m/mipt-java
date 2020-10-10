package edu.phystech;

public interface EntityKey {
    public enum EntityType { ACCOUNT, ENTRY, TRANSACTION };
    public EntityType getType();
    public long getId();
}
