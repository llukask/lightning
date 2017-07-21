package tech.beeflord.lightning.util;

import java.time.Instant;

/**
 * Created by Lukas Kilian on 19/07/17.
 */
public class ValueWithTimestamp<T> {
    private final Instant timestamp;
    private final T value;

    public ValueWithTimestamp(Instant timestamp, T value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public T getValue() {
        return value;
    }
}
