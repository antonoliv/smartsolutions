package saxion.smartsolutions.persistence.impl.jpa.framework;

import saxion.smartsolutions.core.concepts.DomainEntity;

import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaBaseRepository<T extends DomainEntity<I>, K, I extends Comparable<I>> extends JpaAbstractRepository<T, K, I> {

    private static final String SELECT_E_FROM = "SELECT e FROM ";
    private static final String PARAMS_MUST_NOT_BE_NULL_OR_EMPTY = "Params must not be null or empty";
    private static final String QUERY_MUST_NOT_BE_NULL_OR_EMPTY = "query must not be null or empty";
    private static final int DEFAULT_PAGESIZE = 20;

    private final Class<T> entityClass;
    private final String identityFieldName;

    @SuppressWarnings("unchecked")
    public JpaBaseRepository(final String identityFieldName) {
        final boolean isGeneric = getClass().getGenericSuperclass() instanceof ParameterizedType;
        if (!isGeneric) {
            throw new IllegalStateException(
                    "The repository must be parametrized to a specific managed class");
        }

        final ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.identityFieldName = identityFieldName;
    }

    /* package */ JpaBaseRepository(final Class<T> classz, final String identityFieldName) {
        assert classz != null : "you must parametrize the class";
        entityClass = classz;
        this.identityFieldName = identityFieldName;
    }

    public T create(final T entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }

        try {
            entityManager().persist(entity);
        } catch (final PersistenceException ex) {
            throw handlePersistenceException(ex);
        }
        return entity;
    }

    protected Optional<T> read(final K id, final LockModeType locking) {
        return Optional.ofNullable(this.entityManager().find(this.entityClass, id, locking));
    }

    public Optional<T> findById(final K id) {
        if(id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        return read(id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }

    protected String identityFieldName() {
        return identityFieldName;
    }

    public Optional<T> ofIdentity(final I id) {
        return matchOne("e." + identityFieldName() + " = :id", "id", id);
    }

    public void deleteOfIdentity(final I entityId) {
        ofIdentity(entityId).ifPresent(this::delete);
    }

    public T update(final T entity) {
        return save(entity);
    }

    public void delete(T entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }

        try {
            entity = entityManager().merge(entity);
            entityManager().remove(entity);
        } catch (final PersistenceException ex) {
            throw handlePersistenceException(ex);
        }
    }

    public void deleteById(final K entityId) {
        if(entityId == null) {
            throw new IllegalArgumentException("Entity ID cannot be null.");
        }

        // this is not efficient as it will fetch the data first and only
        // afterwards will delete it. a more efficient implementation might
        // issue a DELETE statement directly
        findById(entityId).ifPresent(this::delete);
    }

    public boolean containsKey(final K key) {
        return findById(key).isPresent();
    }

    @Override
    public <S extends T> S save(final S entity) {
        try {
            return entityManager().merge(entity);
        /*} catch (final OptimisticLockException ex) {
            throw new ConcurrencyException(ex);*/
        } catch (final PersistenceException ex) {
            throw handlePersistenceException(ex);
        }
    }

    private RuntimeException handlePersistenceException(final PersistenceException ex) {
        /*if (ex.getCause() instanceof OptimisticLockException) {
            throw new ConcurrencyException(ex);
        }
        if (ex.getCause() instanceof EntityExistsException
                || ex.getCause() instanceof ConstraintViolationException) {
            throw new IntegrityViolationException(ex);
        } else {
            throw ex;
        }*/
        throw ex;
    }

    public T lockItUp(final T entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }

//        try {
            entityManager().lock(entity, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
            return entity;
        /*} catch (final PessimisticLockException e) {
            throw new ConcurrencyException(e);
        }*/
    }

    public Optional<T> lockById(final K id) {
        if(id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        return read(id, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
    }

    public Optional<T> lockOfIdentity(final I id) {
        return matchOne(LockModeType.PESSIMISTIC_FORCE_INCREMENT, "e." + identityFieldName() + " = :id", "id", id);
    }

    protected TypedQuery<T> queryAll() {
        final String className = this.entityClass.getSimpleName();
        return entityManager().createQuery(SELECT_E_FROM + className + " e ", this.entityClass);
    }

    private TypedQuery<T> query(final LockModeType locking, final String where) {
        assert !(where == null || where.trim().isEmpty()): QUERY_MUST_NOT_BE_NULL_OR_EMPTY;

        final String className = this.entityClass.getSimpleName();
        final var query = entityManager().createQuery(SELECT_E_FROM + className + " e WHERE " + where,
                this.entityClass);
        if (locking != null) {
            query.setLockMode(locking);
        }
        return query;
    }

    protected TypedQuery<T> query(final LockModeType locking, final String where, final Map<String, Object> params) {
        assert !(where == null || where.trim().isEmpty()) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;
        assert params != null && params.size() > 0 : PARAMS_MUST_NOT_BE_NULL_OR_EMPTY;

        final TypedQuery<T> q = query(locking, where);
        params.entrySet().stream().forEach(e -> q.setParameter(e.getKey(), e.getValue()));
        return q;
    }

    protected TypedQuery<T> query(final LockModeType locking, final String where, final Object... args) {
        assert !(where == null || where.trim().isEmpty()) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;
        assert args != null && args.length >= 2 : PARAMS_MUST_NOT_BE_NULL_OR_EMPTY;
        assert args.length % 2 == 0 : "uneven number of arguments passed";

        final TypedQuery<T> q = query(locking, where);
        var handleAsArgName = true;
        var argName = "";
        for (final Object o : args) {
            if (handleAsArgName) {
                argName = (String) o;
            } else {
                q.setParameter(argName, o);
            }
            handleAsArgName = !handleAsArgName;
        }
        return q;
    }

    public List<T> first(final int n) {
        if(n <= 0) {
            throw new IllegalArgumentException("N has to be positive.");
        }

        final TypedQuery<T> q = queryAll();
        q.setMaxResults(n);
        return q.getResultList();
    }

    public Optional<T> first() {
        final List<T> r = first(1);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public List<T> page(final int pageNumber, final int pageSize) {
        if(pageNumber <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Arguments have to be positive.");
        }

        final TypedQuery<T> q = queryAll();
        q.setMaxResults(pageSize);
        q.setFirstResult((pageNumber - 1) * pageSize);

        return q.getResultList();
    }

    public Iterator<T> iterator(final int pagesize) {
        return new JpaPagedIterator(this, pagesize);
    }

    public Iterator<T> iterator() {
        return new JpaPagedIterator(this, DEFAULT_PAGESIZE);
    }

    public Iterable<T> findAll() {
        return queryAll().getResultList();
    }

    protected List<T> match(final LockModeType locking, final String where) {
        assert !(where == null || where.trim().isEmpty()) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;

        final TypedQuery<T> q = query(locking, where);
        return q.getResultList();
    }

    protected List<T> match(final String where) {
        return match(null, where);
    }

    protected List<T> match(final LockModeType locking, final String whereWithParameters,
                            final Map<String, Object> params) {
        assert !(whereWithParameters == null || whereWithParameters.trim().isEmpty()) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;
        assert params != null && params.size() > 0 : PARAMS_MUST_NOT_BE_NULL_OR_EMPTY;

        final TypedQuery<T> q = query(locking, whereWithParameters, params);
        return q.getResultList();
    }

    protected List<T> match(final String whereWithParameters,
                            final Map<String, Object> params) {
        return match(null, whereWithParameters, params);
    }

    protected Optional<T> matchOne(final LockModeType locking, final String where) {
        final TypedQuery<T> q = query(locking, where);
        return getSingleResultAsOptional(q);
    }

    protected Optional<T> matchOne(final String where) {
        return matchOne(null, where);
    }

    protected Optional<T> matchOne(final LockModeType locking, final String whereWithParameters,
                                   final Map<String, Object> params) {
        final TypedQuery<T> q = query(locking, whereWithParameters, params);
        return getSingleResultAsOptional(q);
    }

    protected Optional<T> matchOne(final String whereWithParameters, final Map<String, Object> params) {
        return matchOne(null, whereWithParameters, params);
    }

    protected Optional<T> matchOne(final LockModeType locking, final String where, final Object... args) {
        final TypedQuery<T> q = query(locking, where, args);
        return getSingleResultAsOptional(q);
    }

    protected Optional<T> matchOne(final String where, final Object... args) {
        return matchOne(null, where, args);
    }

    protected List<T> match(final LockModeType locking, final String where, final Object... args) {
        final TypedQuery<T> q = query(locking, where, args);
        return q.getResultList();
    }

    protected List<T> match(final String where, final Object... args) {
        return match(null, where, args);
    }

    private Optional<T> getSingleResultAsOptional(final TypedQuery<T> q) {
        try {
            final T ret = q.getSingleResult();
            return Optional.of(ret);
        } catch (@SuppressWarnings("unused") final NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public long count() {
        final TypedQuery<Long> q = createQuery(
                "SELECT COUNT(tablename) FROM " + entityClass.getSimpleName() + " tablename",
                Long.class);
        return q.getSingleResult();
    }

    public long size() {
        return count();
    }

    private class JpaPagedIterator implements Iterator<T> {

        private final JpaBaseRepository<T, K, I> repository;
        private final int pageSize;
        private int currentPageNumber;
        private Iterator<T> currentPage;

        private JpaPagedIterator(final JpaBaseRepository<T, K, I> repository, final int pagesize) {
            this.repository = repository;
            this.pageSize = pagesize;
        }

        @Override
        public boolean hasNext() {
            if (needsToLoadPage()) {
                loadNextPage();
            }
            return this.currentPage.hasNext();
        }

        @Override
        public T next() {
            if (needsToLoadPage()) {
                loadNextPage();
            }
            return this.currentPage.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private void loadNextPage() {
            final List<T> page = this.repository.page(++this.currentPageNumber, this.pageSize);
            this.currentPage = page.iterator();
        }

        private boolean needsToLoadPage() {
            // either we do not have an iterator yet or we have reached the end
            // of the (current) iterator
            return this.currentPage == null || !this.currentPage.hasNext();
        }
    }
}
