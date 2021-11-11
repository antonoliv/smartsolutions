package device.domain;

import javax.persistence.Embeddable;

@Embeddable
public class SerialNumber implements Comparable<SerialNumber> {

    private final String serial;

    private SerialNumber(String serial) {
        if(serial == null || serial.trim().isEmpty()) {
            throw new IllegalArgumentException("Serial number cannot be null or empty!");
        }
        this.serial = serial;
    }

    protected SerialNumber() {
        this.serial = null;
    }

    public String toString() {
        return serial;
    }

    public static SerialNumber valueOf(String serial) {
        return new SerialNumber(serial);
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) return false;
        SerialNumber serial = (SerialNumber) other;
        return this.serial.equals(serial.toString());
    }

    @Override
    public int compareTo(SerialNumber serialNumber) {
        return serial.compareTo(serialNumber.serial);
    }
}
