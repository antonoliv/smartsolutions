package part.domain;

import javax.persistence.Embeddable;

@Embeddable
public class PartNumber {

    private String partnum;

    private PartNumber(String partnum) {
        this.partnum = partnum;
    }


    public static PartNumber valueOf(String partnum) {
        return new PartNumber(partnum);
    }

    public boolean equals(Object otherObject) {
        if(otherObject == null || otherObject.getClass() != this.getClass()) {
            return false;
        }
        PartNumber other = (PartNumber) otherObject;
        return this.partnum.equals(other.partnum);
    }
}
