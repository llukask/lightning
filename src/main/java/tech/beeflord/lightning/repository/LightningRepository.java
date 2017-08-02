package tech.beeflord.lightning.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tech.beeflord.lightning.entity.Lightning;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lukas Kilian on 19/07/17.
 */
public interface LightningRepository extends CrudRepository<Lightning, Long> {
    @Query("select l from Lightning l where l.timestamp >= :timestamp")
    Collection<Lightning> getFrom(@Param("timestamp") Instant timestamp);

    @Query("delete from Lightning l where l.timestamp < :timestamp")
    void deleteTo(@Param("timestamp") Instant timestamp);
}
