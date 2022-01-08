package saxion.smartsolutions.core.part.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Stock {

    private int stock;

    private Stock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock must be positive or 0.");
        }
        this.stock = stock;
    }

    public Stock() {

    }

    public static Stock valueOf(int stock) {
        return new Stock(stock);
    }

    public int getStock() {
        return stock;
    }

    public String toString() {
        return String.valueOf(stock);
    }
}
