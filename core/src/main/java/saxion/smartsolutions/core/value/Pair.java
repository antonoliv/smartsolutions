package saxion.smartsolutions.core.value;

public class Pair<T, K> {

    private T first;
    private K second;

    public Pair(T first, K second) {
        this.first = first;
        this.second = second;
    }

    public T getKey() {
        return first;
    }

    public K getValue() {
        return second;
    }
}
