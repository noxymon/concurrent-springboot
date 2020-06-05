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
    public synchronized void update(Long masterId, Integer sleep) throws RuntimeException, InterruptedException {
        UsageDetail usageDetail = incrementUsageCounter(masterId, sleep);
        // we add scenario for cancel very number 7 customer
        if (usageDetail != null){
            if (usageDetail.getSequenceNumber()%10==7){
                cancelTransactionUsage(masterId,usageDetail);
            }
        }

    }

    private UsageDetail saveToDetail(Long masterId,int sequence)
    {
        UsageDetail usageDetail = new UsageDetail();
        usageDetail.setMasterId(masterId);
        usageDetail.setTranscationId(UUID.randomUUID().toString());
        usageDetail.setSequenceNumber(sequence);
        usageDetail.setCancel(false);
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

    private UsageDetail incrementUsageCounter(Long masterId, Integer sleep) throws RuntimeException, InterruptedException {
        UsageCounterNoVersion usageCounterNoVersion = usageCounterNoOptimisticRepository.updateUsage(masterId, 1);

        Thread.sleep(sleep);
        if (usageCounterNoVersion.getUsage() > usageCounterNoVersion.getMaxCounter()) {
            //rollback because usage is more than allowed usage
            saveFailToDetail(masterId,usageCounterNoVersion.getUsage());
            usageCounterNoOptimisticRepository.updateUsage(masterId, -1);
            return null;
        }else{
            return saveToDetail(masterId,usageCounterNoVersion.getUsage());
        }
    }
    private void cancelTransactionUsage(Long masterId,UsageDetail usageDetail){
        usageCounterNoOptimisticRepository.updateMaxUsage(masterId, 1);
        usageDetail.setCancel(true);
        usageDetailRepository.saveAndFlush(usageDetail);
    }
}
