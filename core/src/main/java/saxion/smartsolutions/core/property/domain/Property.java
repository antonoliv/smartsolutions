package saxion.smartsolutions.core.property.domain;

import org.json.JSONObject;
import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.*;

@Entity
public class Property implements DomainEntity<Designation> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @AttributeOverride(name = "name", column = @Column(name = "generic_name", nullable = false, unique = true))
    private Designation name;


    public Property(Designation name) {
        this.name = name;
    }

    public Property() {

    }


    @Override
    public Designation identity() {
        return name;
    }

    public long getId() {
        return id;
    }

    public Designation getName() {
        return name;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        ret.put("name", name);
        return ret;
    }
}
