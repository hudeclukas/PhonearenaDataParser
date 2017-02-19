package fiit.vi.parser;

/**
 * Created by Lukáš on 15-Oct-16.
 */
public class KeyValue<T> {
    private String key;
    private T value;

    public KeyValue(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key.toLowerCase() + " " + value + "; ";
    }
}
