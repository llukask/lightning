package tech.beeflord.lightning;

import com.satori.rtm.*;
import com.satori.rtm.model.SubscriptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.beeflord.lightning.domain.LightningData;
import tech.beeflord.lightning.util.Consumer;
import tech.beeflord.lightning.util.ValueWithTimestamp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Instant;
import java.util.Set;

/**
 * Created by Lukas Kilian on 19/07/17.
 */
@Component
public class LightningProducer {
    private static final Logger log = LoggerFactory.getLogger(LightningProducer.class);

    private static final String endpoint = "wss://open-data.api.satori.com";
    private final String appkey = "df1A331Bff7aCFbA15d9AD3ce73673bf";
    private final String channel = "full-weather";

    private final Set<Consumer<ValueWithTimestamp<LightningData>>> consumers;

    @Autowired
    public LightningProducer(Set<Consumer<ValueWithTimestamp<LightningData>>> consumers) {
        this.consumers = consumers;
    }

    private RtmClient client;

    @PostConstruct
    private LightningProducer start() {
        log.info("Starting FlashProducer");
        client = new RtmClientBuilder(endpoint, appkey)
                .setListener(new RtmClientAdapter() {
                    @Override
                    public void onEnterConnected(RtmClient client) {
                        super.onEnterConnected(client);

                        log.info("Connected to channel '{}' on endpoint '{}'", channel, endpoint);
                    }
                }).build();

        SubscriptionAdapter listener = new SubscriptionAdapter() {
            @Override
            public void onSubscriptionData(SubscriptionData data) {
                data.getMessages().forEach(a -> {
                    LightningData lt = a.convertToType(LightningData.class);
                    //log.info(lt.toString());
                    if(!"lightning".equals(lt.getType())) {
                        return;
                    }
                    ValueWithTimestamp<LightningData> v =
                            new ValueWithTimestamp<>(Instant.now(), lt);
                    consumers.forEach(consumer -> consumer.accept(v));
                });
            }
        };

        client.createSubscription(channel, SubscriptionMode.SIMPLE, listener);
        client.start();

        return this;
    }

    @PreDestroy
    public void stop() {
        client.stop();
    }

}
