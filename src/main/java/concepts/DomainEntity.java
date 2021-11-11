package concepts;

import java.io.Serializable;

public interface DomainEntity<I extends Comparable<I>> extends Comparable<I>, Serializable {

    I identity();

    default boolean hasIdentity(final I id) {
        return identity().equals(id);
    }

    @Override
    boolean equals(Object other);

    @Override
    int hashCode();

    boolean sameAs(Object other);

    @Override
    default int compareTo(final I other) {
        return identity().compareTo(other);
    }
}
