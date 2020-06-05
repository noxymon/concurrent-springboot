package id.web.noxymon.concurrentspringboot.services;

import id.web.noxymon.concurrentspringboot.repositories.UsageCounterRepository;
import id.web.noxymon.concurrentspringboot.repositories.UsageDetailRepository;
import id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounter;
import id.web.noxymon.concurrentspringboot.repositories.entities.UsageDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateServiceByOptimisticLocking
{
    private final UsageDetailRepository usageDetailRepository;
    private final UsageCounterRepository usageCounterRepository;

    @Transactional
    public void update(Long masterId, Integer maxCounter)
    {
        incrementUsageCounter(masterId, maxCounter);
        saveToDetail(masterId);
    }

    private void saveToDetail(Long masterId)
    {
        UsageDetail usageDetail = new UsageDetail();
        usageDetail.setMasterId(masterId);
        usageDetail.setTranscationId(UUID.randomUUID().toString());
        usageDetailRepository.saveAndFlush(usageDetail);
    }

    private void incrementUsageCounter(Long masterId, Integer maxCounter) throws RuntimeException
    {
        final UsageCounter composedUsageCounter = composeUsageCounter(masterId, maxCounter);

        assertUsageNotExceed(maxCounter, composedUsageCounter);

        composedUsageCounter.setUsage(composedUsageCounter.getUsage() + 1);
        usageCounterRepository.saveAndFlush(composedUsageCounter);
    }

    private UsageCounter composeUsageCounter(Long masterId, Integer maxCounter)
    {
        return usageCounterRepository
                    .findById(masterId)
                    .orElseGet(() -> {
                        UsageCounter usageCounter = new UsageCounter();
                        usageCounter.setMasterId(masterId);
                        usageCounter.setMaxCounter(maxCounter);
                        return usageCounter;
                    });
    }

    private void assertUsageNotExceed(Integer maxCounter, UsageCounter existingUsageCounter)
    {
        if(existingUsageCounter.getUsage() >= maxCounter){
            throw new RuntimeException("Failed !!!");
        }
    }
}