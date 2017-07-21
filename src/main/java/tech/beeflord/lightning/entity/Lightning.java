package tech.beeflord.lightning.entity;

import org.apache.commons.math3.ml.clustering.Clusterable;
import tech.beeflord.lightning.domain.LightningData;
import tech.beeflord.lightning.util.ValueWithTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Created by Lukas Kilian on 19/07/17.
 */
@Entity
@Table(name = "lightning")
public class Lightning implements Clusterable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Instant timestamp;
    @NotNull
    private double lat;
    @NotNull
    private double lon;


    protected Lightning() {}

    public Lightning(ValueWithTimestamp<LightningData> data) {
        this.timestamp = data.getTimestamp();
        this.lat = data.getValue().getLocation().getCoords().getLat();
        this.lon = data.getValue().getLocation().getCoords().getLon();
    }

    public Lightning(Instant timestamp, double lat, double lon) {
        this.timestamp = timestamp;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public double[] getPoint() {
        return new double[] {lat, lon};
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Lightning{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
