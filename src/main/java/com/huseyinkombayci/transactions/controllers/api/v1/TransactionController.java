package com.huseyinkombayci.transactions.controllers.api.v1;

import com.huseyinkombayci.transactions.domains.dtos.BaseTransactionDTO;
import com.huseyinkombayci.transactions.domains.dtos.IdentifiableTransactionDTO;
import com.huseyinkombayci.transactions.domains.models.TransactionStatus;
import com.huseyinkombayci.transactions.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

import static com.huseyinkombayci.transactions.domains.models.Authority.Role.Constants.ADMIN;
import static com.huseyinkombayci.transactions.domains.models.Authority.Role.Constants.USER;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction")
public class TransactionController {

  private final TransactionService transactionService;

  @RolesAllowed({ADMIN, USER})
  @Operation(summary = "Get all transactions")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<IdentifiableTransactionDTO> getAllTransactions(
      @Parameter(description = "Filter transactions")
      @RequestParam(required = false) TransactionStatus status
  ) {
    if (status != null) {
      return transactionService.getAllTransactionsByStatus(status);
    }
    return transactionService.getAllTransactions();
  }

  @RolesAllowed({ADMIN, USER})
  @Operation(summary = "Get a transaction by id")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public IdentifiableTransactionDTO getTransactionById(@PathVariable Long id) {
    return transactionService.getTransactionById(id);
  }

  @RolesAllowed(ADMIN)
  @Operation(summary = "Create a new transaction")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public IdentifiableTransactionDTO createNewTransaction(@RequestBody @Valid BaseTransactionDTO baseTransactionDTO) {
    return transactionService.createNewTransaction(baseTransactionDTO);
  }

  @RolesAllowed(ADMIN)
  @Operation(summary = "Update existing transaction")
  @PutMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public BaseTransactionDTO updateTransaction(@Parameter(description = "The transaction id", required = true) @PathVariable Long id,
                                              @RequestBody @Valid BaseTransactionDTO baseTransactionDTO){
    return transactionService.updateTransaction(id, baseTransactionDTO);
  }

  @RolesAllowed(ADMIN)
  @Operation(summary = "Patch existing transaction")
  @PatchMapping({"/{id}"})
  @ResponseStatus(HttpStatus.OK)
  public BaseTransactionDTO patchTransaction(@Parameter(description = "The transaction id", required = true) @PathVariable Long id,
                                             @RequestBody BaseTransactionDTO baseTransactionDTO){
    return transactionService.patchTransaction(id, baseTransactionDTO);
  }

  @RolesAllowed(ADMIN)
  @Operation(summary = "Delete a transaction")
  @DeleteMapping({"/{id}"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTransaction(@Parameter(description = "The transaction id", required = true) @PathVariable Long id){
    transactionService.deleteTransactionById(id);
  }
}
