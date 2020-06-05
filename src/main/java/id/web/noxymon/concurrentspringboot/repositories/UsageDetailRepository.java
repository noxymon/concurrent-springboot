package id.web.noxymon.concurrentspringboot.repositories;

import id.web.noxymon.concurrentspringboot.repositories.entities.UsageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageDetailRepository extends JpaRepository<UsageDetail, UsageDetail.PrimaryKeys>
{
}
