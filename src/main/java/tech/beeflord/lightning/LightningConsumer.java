package tech.beeflord.lightning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.beeflord.lightning.domain.LightningData;
import tech.beeflord.lightning.entity.Lightning;
import tech.beeflord.lightning.repository.LightningRepository;
import tech.beeflord.lightning.util.Consumer;
import tech.beeflord.lightning.util.ValueWithTimestamp;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Lukas Kilian on 19/07/17.
 */
@Service
public class LightningConsumer implements Consumer<ValueWithTimestamp<LightningData>> {
    private final static Logger log = LoggerFactory.getLogger(LightningConsumer.class);

    private final List<ValueWithTimestamp<LightningData>> data;

    private final LightningRepository repository;

    @Autowired
    public LightningConsumer(LightningRepository repository) {
        this.repository = repository;
        data = new ArrayList<>();
    }

    @Override
    public void accept(ValueWithTimestamp<LightningData> ld) {
        // data.add(ld);
        repository.save(new Lightning(ld));
    }

    public Collection<Lightning> getLightningData(long seconds) {
        final Instant from = Instant.now().minus(seconds, ChronoUnit.SECONDS);
        return repository.getFrom(from);
    }
}
