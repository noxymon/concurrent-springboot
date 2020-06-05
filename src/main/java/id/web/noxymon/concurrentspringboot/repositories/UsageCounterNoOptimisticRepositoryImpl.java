package id.web.noxymon.concurrentspringboot.repositories;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UsageCounterNoOptimisticRepositoryImpl implements UsageCounterNoOptimisticRepository
{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public int incrementUsage(Long masterId, Integer maxCounter)
    {
        return (int) entityManager.createNativeQuery("insert into usage_counter (master_id, max_counter, \"usage\") values (:masterId, :maxCounter, 1) " +
                                                "on conflict on constraint usage_counter_pk do " +
                                                "update set \"usage\" = EXCLUDED.\"usage\" + 1 where usage_counter.master_id = :masterId returning \"usage\"")
                                  .setParameter("masterId", masterId)
                                  .setParameter("maxCounter", maxCounter)
                                  .getSingleResult();
    }
}