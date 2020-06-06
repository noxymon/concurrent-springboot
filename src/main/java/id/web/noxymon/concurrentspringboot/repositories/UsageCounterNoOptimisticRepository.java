package id.web.noxymon.concurrentspringboot.repositories;

import id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounterNoVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageCounterNoOptimisticRepository extends JpaRepository<UsageCounterNoVersion, Long>
{
    @Query(value = "update usage_counter set usage=usage+1 where master_id=:masterId returning * ", nativeQuery = true)
    UsageCounterNoVersion updateUsage(@Param("masterId") long masterId);

    @Query(value = "update usage_counter set usage=usage-1 where master_id=:masterId returning * ",nativeQuery = true)
    UsageCounterNoVersion decreaseUsage(@Param("masterId") long masterId);
}