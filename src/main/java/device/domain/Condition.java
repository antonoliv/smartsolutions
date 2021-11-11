package device.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Condition {

    private final ConditionLevel level;

    private final String observation;

    public enum ConditionLevel {
        VERY_BAD("Very Bad"),
        BAD("Bad"),
        OK("Ok"),
        GOOD("Good"),
        VERY_GOOD("Very Good");

        private final String name;

        ConditionLevel(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    private Condition(ConditionLevel level, String observation) {
        if (level == null || observation == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        if (observation.trim().isEmpty()) {
            throw new IllegalArgumentException("Observation cannot be empty.");
        }
        this.level = level;
        this.observation = observation;
    }

    private Condition(ConditionLevel level) {
        if (level == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.level = level;
        this.observation = null;
    }

    protected Condition() {
        level = null;
        observation = null;
    }

    public String toString() {
        if(observation != null) {
            return level.toString() + ": " + observation;
        }
        return level.toString();
    }

    public static Condition valueOf(ConditionLevel level) {
        return new Condition(level);
    }

    public static Condition valueOf(ConditionLevel level, String observation) {
        return new Condition(level, observation);
    }
}
