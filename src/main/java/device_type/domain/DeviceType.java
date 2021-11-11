package device_type.domain;

import value.Designation;

import javax.persistence.*;

@Entity
public class DeviceType {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    private Designation name;

    public DeviceType(Designation name) {
        if(name == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
    }


}
