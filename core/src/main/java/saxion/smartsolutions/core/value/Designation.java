package saxion.smartsolutions.core.value;

import javax.persistence.Embeddable;

@Embeddable
public class Designation implements Comparable<Designation> {

    private final String name;

    private Designation(String name) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Desgination cannot be null or empty.");
        }
        this.name = name;
    }

    protected Designation() {
        this.name = null;
    }

    public String toString() {
        return name;
    }

    public static Designation valueOf(String desgination) {
        return new Designation(desgination);
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Designation des = (Designation) other;
        return this.name.equals(des.toString());
    }

    @Override
    public int compareTo(Designation designation) {
        return this.name.compareTo(designation.name);
    }
}
