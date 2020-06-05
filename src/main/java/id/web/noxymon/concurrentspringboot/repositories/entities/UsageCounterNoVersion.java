package id.web.noxymon.concurrentspringboot.repositories.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Data
@Entity(name = "id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounterNoVersion")
@Table(name = "usage_counter")
public class UsageCounterNoVersion
{

  @Id
  @Column(name = "\"master_id\"", nullable = false)
  private Long masterId;
  @Column(name = "\"max_counter\"", nullable = true)
  private Integer maxCounter;
  @Column(name = "\"usage\"", nullable = true)
  private Integer usage = 0;
}