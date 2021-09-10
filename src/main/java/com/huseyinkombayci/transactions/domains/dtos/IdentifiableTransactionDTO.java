package com.huseyinkombayci.transactions.domains.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class IdentifiableTransactionDTO extends BaseTransactionDTO implements IdentifiableDTO {

  @Schema(description = "Transaction id")
  private Long id;

  @JsonProperty("created_date")
  @Schema(description = "Transaction created date")
  private LocalDateTime createdDate;

  @JsonProperty("modified_date")
  @Schema(description = "Transaction modified date")
  private LocalDateTime modifiedDate;

}
