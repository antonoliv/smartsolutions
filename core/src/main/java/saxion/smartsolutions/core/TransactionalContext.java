package saxion.smartsolutions.core;

public interface TransactionalContext {

    void beginTransaction();

    void commit();

    void rollback();

    void close();

    boolean isActive();
}

