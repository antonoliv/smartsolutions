package device.domain;

import concepts.DomainEntity;
import model.domain.Model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Device implements DomainEntity<Device> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    private SerialNumber serial;

    private final Date entryDate;

    private Condition condition;

    @ManyToOne
    private Model model;

    @ElementCollection
    private Set<Image> images;

    public Device(SerialNumber serial, Date entryDate, Model model, Condition condition, Set<Image> images) {
        if(serial == null || entryDate == null || model == null || images == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.serial = serial;
        this.entryDate = entryDate;
        this.model = model;
        this.condition = condition;
        this.images = new HashSet<>(images);
    }

    public Device(SerialNumber serial, Date entryDate, Model model, Condition condition) {
        if(serial == null || entryDate == null || model == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.serial = serial;
        this.entryDate = entryDate;
        this.model = model;
        this.condition = condition;
        this.images = new HashSet<>();
    }

    public Device() {
        this.serial = null;
        this.entryDate = null;
        this.model = null;
        this.images = null;
    }


    @Override
    public Device identity() {
        return null;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }
}
