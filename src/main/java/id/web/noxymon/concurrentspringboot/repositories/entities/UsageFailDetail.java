package id.web.noxymon.concurrentspringboot.repositories.entities;

import java.io.Serializable;
import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "id.web.noxymon.concurrentspringboot.repositories.entities.UsageFailDetail")
@Table(name = "usage_fail_detail")
@IdClass(UsageFailDetail.PrimaryKeys.class)
public class UsageFailDetail {
  @Data
  public static class PrimaryKeys implements Serializable {
    private Long masterId;
    private String transcationId;
  }

  @Id
  @Column(name = "\"master_id\"", nullable = false)
  private Long masterId;
  @Id
  @Column(name = "\"transcation_id\"", nullable = false)
  private String transcationId;
  @Column(name = "\"sequence_number\"", nullable = true)
  private Integer sequenceNumber;
}