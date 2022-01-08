package saxion.smartsolutions.core.model.domain;

import javax.persistence.Embeddable;

/**
 * Represents a model number in the application
 */
@Embeddable
public class ModelNumber implements Comparable<ModelNumber> {

    /**
     * Model number in primitive type
     */
    private final String modelNumber;

    /**
     * Constructs a model number with the given string
     * @param modelNumber model number
     */
    private ModelNumber(String modelNumber) {
        if (modelNumber == null || modelNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Model Number cannot be null or empty.");
        }
        this.modelNumber = modelNumber;
    }

    // Empty Constructor
    protected ModelNumber() {
        modelNumber = null;
    }

    /**
     * Returns a model number with the given value
     * @param modelNumber model number
     * @return instance of ModelNumber
     */
    public static ModelNumber valueOf(String modelNumber) {
        return new ModelNumber(modelNumber);
    }

    /**
     * Returns a string representation of the model number
     * @return string of model number
     */
    public String toString() {
        return modelNumber;
    }

    /**
     * Checks if model number is equal to another object
     * @param other other object
     * @return true if equal
     */
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        ModelNumber model = (ModelNumber) other;
        return this.modelNumber.equals(model.toString());
    }

    /**
     * Compares model number with another one
     * @param modelNumber other model number
     * @return 0 if equal<br>
     * >0 if model number is greater than the other<br>
     * <0 if model number is less than the other
     */
    @Override
    public int compareTo(ModelNumber modelNumber) {
        return this.modelNumber.compareTo(modelNumber.modelNumber);
    }
}
