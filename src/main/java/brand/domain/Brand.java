package brand.domain;

import concepts.DomainEntity;
import value.Designation;

import javax.persistence.*;

@Entity
public class Brand implements DomainEntity<Designation> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    private Designation name;

    public Brand(Designation name) {
        this.name = name;
    }

    public Brand() {
        this.name = null;
    }

    @Override
    public Designation identity() {
        return name;
    }

    @Override
    public boolean sameAs(Object other) {
        if(other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Brand oth = (Brand) other;
        return name.equals(oth.name);
    }
}
