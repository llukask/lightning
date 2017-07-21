package tech.beeflord.lightning.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Lukas Kilian on 19/07/17.
 */
public class LightningData {
    public final class Location {
        private final Coordinates coords;
        private final long polarity, maxDeviationSpanNs, maxCircularGap;

        @JsonCreator
        public Location(@JsonProperty("coords") Coordinates coords,
                        @JsonProperty("polarity") long polarity,
                        @JsonProperty("max_deviation_span_ns") long maxDeviationSpanNs,
                        @JsonProperty("max_circular_gap") long maxCircularGap) {
            this.coords = coords;
            this.polarity = polarity;
            this.maxDeviationSpanNs = maxDeviationSpanNs;
            this.maxCircularGap = maxCircularGap;
        }

        public Coordinates getCoords() {
            return coords;
        }

        public long getPolarity() {
            return polarity;
        }

        public long getMaxDeviationSpanNs() {
            return maxDeviationSpanNs;
        }

        public long getMaxCircularGap() {
            return maxCircularGap;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "coords=" + coords +
                    ", polarity=" + polarity +
                    ", maxDeviationSpanNs=" + maxDeviationSpanNs +
                    ", maxCircularGap=" + maxCircularGap +
                    '}';
        }
    }

    private final String type;
    private final Location location;

    @JsonCreator
    public LightningData(@JsonProperty("type") String type,
                         @JsonProperty("location") Location location) {
        this.type = type;
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "LightningData{" +
                "type='" + type + '\'' +
                ", location=" + location +
                '}';
    }
}