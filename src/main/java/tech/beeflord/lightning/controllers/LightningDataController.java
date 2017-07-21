package tech.beeflord.lightning.controllers;

import io.prometheus.client.spring.web.PrometheusTimeMethod;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.beeflord.lightning.LightningConsumer;
import tech.beeflord.lightning.domain.Coordinates;
import tech.beeflord.lightning.domain.LightningData;
import tech.beeflord.lightning.entity.Lightning;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Lukas Kilian on 19/07/17.
 */
@RestController
@RequestMapping("/lightning")
public class LightningDataController {
    private static final Logger log = LoggerFactory.getLogger(LightningDataController.class);
    private final LightningConsumer consumer;

    @Autowired
    public LightningDataController(LightningConsumer consumer) {
        this.consumer = consumer;
    }

    @PrometheusTimeMethod(name = "lightning_get_seconds", help = "The number of seconds taken by the '/lightning' handler")
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody Collection<Lightning> lightningData(
            @RequestParam(name = "window", defaultValue = "60") long window) {
        final Collection<Lightning> lightnings = consumer.getLightningData(window);

        log.debug("Retreived {} data points!", lightnings.size());

        return lightnings;
    }

    @PrometheusTimeMethod(name = "lightning_cluster_seconds", help = "The number of seconds tyken by the '/lightning/cluster' handler")
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/clusters", method =  RequestMethod.GET)
    public @ResponseBody List<List<Lightning>> clusters(
            @RequestParam(name = "window", defaultValue = "60") long window,
            @RequestParam(name = "eps", defaultValue = "1.0") double eps,
            @RequestParam(name = "minPts", defaultValue = "3") int minPts) {
        DBSCANClusterer<Lightning> clusterer = new DBSCANClusterer<>(eps, minPts);

        final Collection<Lightning> lightnings = consumer.getLightningData(window);

        log.debug("Clustering {} data points!", lightnings.size());


        List<Cluster<Lightning>> clusters = clusterer.cluster(lightnings);

        return clusters.stream().map(Cluster::getPoints).collect(Collectors.toList());
    }
}
