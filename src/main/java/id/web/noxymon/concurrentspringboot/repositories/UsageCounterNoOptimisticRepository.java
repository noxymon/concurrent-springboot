package id.web.noxymon.concurrentspringboot.repositories;

import org.springframework.data.repository.query.Param;

public interface UsageCounterNoOptimisticRepository
{
    int incrementUsage(@Param("masterId") Long masterId, Integer maxCounter);
}