package id.web.noxymon.concurrentspringboot.repositories;

import id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounter;
import id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounterNoVersion;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Map;

@Repository
public interface UsageCounterNoOptimisticRepository extends JpaRepository<UsageCounterNoVersion, Long>
{

    @Query(value = "update usage_counter set usage=usage+:usage where master_id=:masterId returning * ",nativeQuery = true)
    UsageCounterNoVersion updateUsage(@Param("masterId") long masterId, @Param("usage") long usage);

    @Query(value = "update usage_counter set max_counter=max_counter+:usage where master_id=:masterId returning * ",nativeQuery = true)
    UsageCounterNoVersion updateMaxUsage(@Param("masterId") long masterId, @Param("usage") long usage);

}
