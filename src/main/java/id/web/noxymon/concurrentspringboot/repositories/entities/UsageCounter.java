package id.web.noxymon.concurrentspringboot.repositories.entities;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "id.web.noxymon.concurrentspringboot.repositories.entities.UsageCounter")
@Table(name = "usage_counter")
public class UsageCounter {

  @Id
  @Column(name = "\"master_id\"", nullable = false)
  private Long masterId;
  @Column(name = "\"max_counter\"", nullable = true)
  private Integer maxCounter;
  @Column(name = "\"usage\"", nullable = true)
  private Integer usage;
  @Column(name = "\"max_usage\"", nullable = true)
  private Integer maxUsage;
  @Column(name = "\"version\"", nullable = true)
  private Integer version;
}