package id.web.noxymon.concurrentspringboot.repositories;

import id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageCounterRepository extends JpaRepository<UsageCounter, Long>
{
}
