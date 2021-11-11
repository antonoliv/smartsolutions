package device.domain;

import javax.persistence.Embeddable;


public enum State {
    UNKNOWN,
    REPAIR,
    RECYCLE,
    WORKING,
    BROKEN
}
