package edu.phystech;

public class SimpleEntityKey implements EntityKey {
    private EntityType type;
    private long id;

    public SimpleEntityKey(EntityType type, long id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public EntityType getType() {
        return type;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof EntityKey)) return false;
        return (this.getId() == ((EntityKey)obj).getId()) && (this.getType() == ((EntityKey)obj).getType());
    }

    @Override
    public int hashCode() {
        return type.hashCode() + String.valueOf(id).hashCode();
    }
}
