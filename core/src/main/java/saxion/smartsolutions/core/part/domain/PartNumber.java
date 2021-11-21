package saxion.smartsolutions.core.part.domain;

import javax.persistence.Embeddable;

@Embeddable
public class PartNumber implements Comparable<PartNumber> {

    private String partnum;

    private PartNumber(String partnum) {
        this.partnum = partnum;
    }

    protected PartNumber() {
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

    @Override
    public int compareTo(PartNumber partNumber) {
        return this.partnum.compareTo(partNumber.partnum);
    }
}
