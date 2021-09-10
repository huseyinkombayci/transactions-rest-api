package com.huseyinkombayci.transactions.domains.dtos;

import com.huseyinkombayci.transactions.domains.models.TransactionStatus;
import com.huseyinkombayci.transactions.domains.validators.ValidCurrency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseTransactionDTO {

  @NotNull
  @Schema(description = "Transaction status. Supported values - CREATED and PROCESSING")
  private TransactionStatus status;

  @NotNull
  @Positive
  @Schema(description = "Transaction amount. Must be a positive value")
  private BigDecimal amount;

  @NotBlank
  @ValidCurrency
  @Schema(description = "Transaction currency. Supported values - GBP, EUR, and USD")
  private String currency;

  @NotNull
  @Schema(description = "Transaction description")
  private String description;
}
