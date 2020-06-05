package id.web.noxymon.concurrentspringboot.repositories;

import id.web.noxymon.concurrentspringboot.repositories.entities.UsageFailDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by rickylazuardy on 6/5/20.
 */

@Repository
public interface UsageFailDetailRepository extends JpaRepository<UsageFailDetail, UsageFailDetail.PrimaryKeys>
{
}

