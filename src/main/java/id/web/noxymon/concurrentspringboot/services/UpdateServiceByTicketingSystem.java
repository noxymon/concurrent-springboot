package id.web.noxymon.concurrentspringboot.services;

import id.web.noxymon.concurrentspringboot.repositories.UsageCounterNoOptimisticRepository;
import id.web.noxymon.concurrentspringboot.repositories.UsageDetailRepository;
import id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounterNoVersion;
import id.web.noxymon.concurrentspringboot.repositories.entities.UsageDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UpdateServiceByTicketingSystem
{
    private final UsageDetailRepository usageDetailRepository;
    private final UsageCounterNoOptimisticRepository usageCounterNoOptimisticRepository;

    @Transactional
    public synchronized void update(Long masterId, Integer maxCounter) throws RuntimeException
    {
        incrementUsageCounter(masterId, maxCounter);
        saveToDetail(masterId);
    }

    private UsageDetail saveToDetail(Long masterId)
    {
        UsageDetail usageDetail = new UsageDetail();
        usageDetail.setMasterId(masterId);
        usageDetail.setTranscationId(UUID.randomUUID().toString());
        return usageDetailRepository.saveAndFlush(usageDetail);
    }

    private void incrementUsageCounter(Long masterId, Integer maxCounter) throws RuntimeException
    {
        final UsageCounterNoVersion usageCounter = composeUsageCounter(masterId, maxCounter);
        usageCounter.setUsage(usageCounter.getUsage() + 1);

        final UsageCounterNoVersion usageCounterAfterSave = usageCounterNoOptimisticRepository.saveAndFlush(usageCounter);

        if (usageCounterAfterSave.getUsage() > maxCounter) {
            throw new RuntimeException("Failed !!");
        }
    }

    private UsageCounterNoVersion composeUsageCounter(Long masterId, Integer maxCounter)
    {
        return usageCounterNoOptimisticRepository
                .findById(masterId)
                .orElseGet(() -> {
                    UsageCounterNoVersion usageCounterNoVersion = new UsageCounterNoVersion();
                    usageCounterNoVersion.setMasterId(masterId);
                    usageCounterNoVersion.setMaxCounter(maxCounter);
                    usageCounterNoVersion.setUsage(0);
                    return usageCounterNoVersion;
                });
    }
}
