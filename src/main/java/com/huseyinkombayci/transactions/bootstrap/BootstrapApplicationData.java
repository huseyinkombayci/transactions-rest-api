package com.huseyinkombayci.transactions.bootstrap;

import com.huseyinkombayci.transactions.domains.dtos.BaseTransactionDTO;
import com.huseyinkombayci.transactions.domains.dtos.RegisterUserDTO;
import com.huseyinkombayci.transactions.domains.models.Authority;
import com.huseyinkombayci.transactions.domains.models.TransactionStatus;
import com.huseyinkombayci.transactions.services.TransactionService;
import com.huseyinkombayci.transactions.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BootstrapApplicationData implements ApplicationListener<ApplicationReadyEvent> {

  private final UserService userService;
  private final TransactionService transactionService;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    RegisterUserDTO dto = new RegisterUserDTO();
    dto.setUsername("alan.turing@test.com");
    dto.setPassword("test-pass");
    dto.setAuthorities(Set.of(new Authority(Authority.Role.Constants.ADMIN)));
    userService.createNewUser(dto);

    BaseTransactionDTO transactionDTO = new BaseTransactionDTO();
    transactionDTO.setAmount(BigDecimal.valueOf(25.60));
    transactionDTO.setCurrency("GBP");
    transactionDTO.setStatus(TransactionStatus.CREATED);
    transactionDTO.setDescription("Transaction 1");
    transactionService.createNewTransaction(transactionDTO);
  }
}
