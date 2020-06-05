package id.web.noxymon.concurrentspringboot.repositories;

import id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounterNoVersion;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

@Repository
public interface UsageCounterNoOptimisticRepository extends JpaRepository<UsageCounterNoVersion, Long>
{
}
