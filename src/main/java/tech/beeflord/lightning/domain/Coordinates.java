package tech.beeflord.lightning.domain;

/**
 * Created by Lukas Kilian on 19/07/17.
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.math3.ml.clustering.Clusterable;

public class Coordinates implements Clusterable {
    private final double lat, lon, alt;

    @JsonCreator
    public Coordinates(@JsonProperty("lat") double lat,
                       @JsonProperty("lon") double lon,
                       @JsonProperty("alt") double alt) {
        this.lon = lon;
        this.lat = lat;
        this.alt = alt;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public double getAlt() {
        return alt;
    }

    @Override
    @JsonIgnore
    public double[] getPoint() {
        return new double[] {lat, lon};
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", alt=" + alt +
                '}';
    }
}