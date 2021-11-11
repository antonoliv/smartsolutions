package device.domain;

import javax.persistence.Embeddable;
import java.io.File;

@Embeddable
public class Image {

    public final File image;

    private Image(File image) {
        if(image == null) {
            throw new IllegalArgumentException("Image cannot be null.");
        }
        this.image = image;
    }

    protected Image() {
        this.image = null;
    }

    public static Image valueOf(File image) {
        return new Image(image);
    }

    public String toString() {
        return image.toString();
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Image image = (Image) other;
        return this.image.equals(other);
    }

}
