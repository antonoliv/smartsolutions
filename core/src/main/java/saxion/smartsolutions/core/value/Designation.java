package saxion.smartsolutions.core.value;

import javax.persistence.Embeddable;

/**
 * Represents a designation in the application
 */
@Embeddable
public class Designation implements Comparable<Designation> {

    /**
     * String representation of designation
     */
    private final String name;

    /**
     * Constructs a designation with the given value
     * @param name
     */
    private Designation(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Desgination cannot be null or empty.");
        }
        this.name = name;
    }

    // Empty Constructor
    protected Designation() {
        this.name = null;
    }

    /**
     * Returns a designation with the given value
     * @param desgination
     * @return
     */
    public static Designation valueOf(String desgination) {
        return new Designation(desgination);
    }

    /**
     * Returns a string representation of the designation
     * @return string representation of designation
     */
    public String toString() {
        return name;
    }

    /**
     * Checks of designation is equal to another object
     * @param other other object
     * @return true if equal
     */
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Designation des = (Designation) other;
        return this.name.equals(des.toString());
    }

    /**
     * Compares two designations
     * @param designation other designation
     * @return 0 if equal<br>
     * >0 if designation is greater than the other<br>
     * <0 if designation is less than the other
     */
    @Override
    public int compareTo(Designation designation) {
        return this.name.compareTo(designation.name);
    }
}
