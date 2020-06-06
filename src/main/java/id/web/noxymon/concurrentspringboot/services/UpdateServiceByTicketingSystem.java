package id.web.noxymon.concurrentspringboot.services;

import id.web.noxymon.concurrentspringboot.repositories.UsageCounterNoOptimisticRepository;
import id.web.noxymon.concurrentspringboot.repositories.UsageDetailRepository;
import id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounterNoVersion;
import id.web.noxymon.concurrentspringboot.repositories.entities.UsageDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateServiceByTicketingSystem
{
    private final UsageDetailRepository usageDetailRepository;
    private final UsageCounterNoOptimisticRepository usageCounterNoOptimisticRepository;

    public void update(Long masterId, Integer maxCounter) throws RuntimeException
    {
        incrementUsageCounter(masterId, maxCounter);
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
        final UsageCounterNoVersion usageAfterIncrement = usageCounterNoOptimisticRepository.updateUsage(masterId);
        if (usageAfterIncrement.getUsage() > maxCounter) {
            usageCounterNoOptimisticRepository.decreaseUsage(masterId);
            throw new RuntimeException("Failed !!");
        }else{
            saveToDetail(masterId);
        }
    }
}
