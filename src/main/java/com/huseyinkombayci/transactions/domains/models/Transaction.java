package com.huseyinkombayci.transactions.domains.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime modifiedDate;

  @Column
  @Enumerated(EnumType.STRING)
  private TransactionStatus status;

  @Column
  @DecimalMin(value = "0.0", inclusive = false)
  private BigDecimal amount;

  @Column
  private String currency;

  @Column(columnDefinition = "TEXT")
  private String description;

}