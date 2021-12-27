package saxion.smartsolutions.core.model.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ModelNumber implements Comparable<ModelNumber> {

    public final String model;

    private ModelNumber(String model) {
        if(model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model Number cannot be null or empty.");
        }
        this.model = model;
    }

    protected ModelNumber() {
        this.model = null;
    }

    public String toString() {
        return model;
    }

    public static ModelNumber valueOf(String model) {
        return new ModelNumber(model);
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        ModelNumber model = (ModelNumber) other;
        return this.model.equals(model.toString());
    }

    @Override
    public int compareTo(ModelNumber modelNumber) {
        return this.model.compareTo(modelNumber.model);
    }
}
