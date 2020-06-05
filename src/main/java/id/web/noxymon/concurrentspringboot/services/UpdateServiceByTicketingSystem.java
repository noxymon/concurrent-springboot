package id.web.noxymon.concurrentspringboot.services;

import id.web.noxymon.concurrentspringboot.repositories.UsageCounterNoOptimisticRepository;
import id.web.noxymon.concurrentspringboot.repositories.UsageDetailRepository;
import id.web.noxymon.concurrentspringboot.repositories.UsageFailDetailRepository;
import id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounter;
import id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounterNoVersion;
import id.web.noxymon.concurrentspringboot.repositories.entities.UsageDetail;
import id.web.noxymon.concurrentspringboot.repositories.entities.UsageFailDetail;
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
    private final UsageFailDetailRepository usageFailDetailRepository;
    private final UsageCounterNoOptimisticRepository usageCounterNoOptimisticRepository;

    @Transactional
    public synchronized void update(Long masterId, Integer maxCounter) throws RuntimeException
    {
        incrementUsageCounter(masterId, maxCounter);
    }

    private UsageDetail saveToDetail(Long masterId,int sequence)
    {
        UsageDetail usageDetail = new UsageDetail();
        usageDetail.setMasterId(masterId);
        usageDetail.setTranscationId(UUID.randomUUID().toString());
        usageDetail.setSequenceNumber(sequence);
        return usageDetailRepository.saveAndFlush(usageDetail);
    }

    private UsageFailDetail saveFailToDetail(Long masterId,int sequence)
    {
        UsageFailDetail usageFailDetail = new UsageFailDetail();
        usageFailDetail.setMasterId(masterId);
        usageFailDetail.setTranscationId(UUID.randomUUID().toString());
        usageFailDetail.setSequenceNumber(sequence);
        return usageFailDetailRepository.saveAndFlush(usageFailDetail);
    }

    private void incrementUsageCounter(Long masterId, Integer maxCounter) throws RuntimeException
    {
        UsageCounterNoVersion usageCounterNoVersion = usageCounterNoOptimisticRepository.updateUsage(masterId, 1);
        if (usageCounterNoVersion.getUsage() > maxCounter) {
            //rollback
            saveFailToDetail(masterId,usageCounterNoVersion.getUsage());
            usageCounterNoOptimisticRepository.updateUsage(masterId, -1);
        }else{
            saveToDetail(masterId,usageCounterNoVersion.getUsage());
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
