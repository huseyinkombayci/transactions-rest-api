package com.huseyinkombayci.transactions.services;

import com.huseyinkombayci.transactions.domains.dtos.BaseTransactionDTO;
import com.huseyinkombayci.transactions.domains.dtos.IdentifiableTransactionDTO;
import com.huseyinkombayci.transactions.domains.exceptions.TransactionNotFoundException;
import com.huseyinkombayci.transactions.domains.mappers.TransactionMapper;
import com.huseyinkombayci.transactions.domains.models.Transaction;
import com.huseyinkombayci.transactions.domains.models.TransactionStatus;
import com.huseyinkombayci.transactions.repositories.TransactionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

  @Mock
  private TransactionRepository transactionRepository;

  @Spy
  private TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

  @Autowired
  @InjectMocks
  private DefaultTransactionService transactionService;

  private Transaction transaction1;
  private IdentifiableTransactionDTO identifiableTransactionDTO1;
  private List<Transaction> transactions;

  @BeforeEach
  public void setUp() {
    transaction1 = new Transaction(1L, LocalDateTime.now(), LocalDateTime.now(), TransactionStatus.CREATED, BigDecimal.valueOf(100.0), "GBP", "Test Transaction 1");
    identifiableTransactionDTO1 = transactionMapper.transactionToIdentifiableTransactionDTO(transaction1);
    transactions = List.of(transaction1);
  }

  @Test
  @DisplayName("ok :: get all transactions")
  void testGetAllTransactions() {
    when(transactionRepository.findAll()).thenReturn(transactions);

    List<IdentifiableTransactionDTO> list = transactionService.getAllTransactions();

    assertThat(list, hasItems(identifiableTransactionDTO1));
    verify(transactionRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("ok :: get all transactions by status")
  void testGetAllTransactionsByStatus() {
    when(transactionRepository.findAllByStatus(TransactionStatus.CREATED)).thenReturn(List.of(transaction1));

    List<IdentifiableTransactionDTO> list = transactionService.getAllTransactionsByStatus(TransactionStatus.CREATED);

    assertThat(list, hasItems(identifiableTransactionDTO1));
    verify(transactionRepository, times(1)).findAllByStatus(any());
  }

  @Test
  @DisplayName("ok :: get transaction by id")
  void testGetTransactionsById() {
    when(transactionRepository.findById(any())).thenReturn(Optional.of(transaction1));

    IdentifiableTransactionDTO transaction = transactionService.getTransactionById(1L);

    assertThat(transaction, notNullValue());
    assertThat(transaction.getAmount(), Matchers.is(BigDecimal.valueOf(100.0)));
    assertThat(transaction.getCurrency(), Matchers.is("GBP"));
    verify(transactionRepository, times(1)).findById(anyLong());
  }

  @Test
  @DisplayName("not found :: get transaction by id")
  void testGetTransactionsByIdNotFound() {
    when(transactionRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(TransactionNotFoundException.class, () -> transactionService.getTransactionById(3L));
    verify(transactionRepository, times(1)).findById(anyLong());
  }

  @Test
  @DisplayName("ok :: create new transaction")
  void testCreateNewTransaction() {
    BaseTransactionDTO transactionDTO = new BaseTransactionDTO();
    when(transactionRepository.save(any())).thenReturn(transaction1);

    IdentifiableTransactionDTO savedTransaction = transactionService.createNewTransaction(transactionDTO);

    assertThat(savedTransaction, notNullValue());
    assertThat(savedTransaction.getAmount(), Matchers.is(BigDecimal.valueOf(100.0)));
    assertThat(savedTransaction.getCurrency(), Matchers.is("GBP"));
    verify(transactionRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("ok :: update existing transaction")
  void testUpdateTransaction() {
    BaseTransactionDTO transactionDTO = new BaseTransactionDTO();
    when(transactionRepository.save(any())).thenReturn(transaction1);

    BaseTransactionDTO savedTransaction = transactionService.updateTransaction(1L, transactionDTO);

    assertThat(savedTransaction, notNullValue());
    assertThat(savedTransaction.getAmount(), Matchers.is(BigDecimal.valueOf(100.0)));
    assertThat(savedTransaction.getCurrency(), Matchers.is("GBP"));
    verify(transactionRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("ok :: patch existing transaction")
  void testPatchTransaction() {
    BaseTransactionDTO transactionDTO = new BaseTransactionDTO();
    when(transactionRepository.save(any())).thenReturn(transaction1);
    when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction1));

    BaseTransactionDTO savedTransaction = transactionService.patchTransaction(1L, transactionDTO);

    assertThat(savedTransaction, notNullValue());
    assertThat(savedTransaction.getAmount(), Matchers.is(BigDecimal.valueOf(100.0)));
    assertThat(savedTransaction.getCurrency(), Matchers.is("GBP"));
    verify(transactionRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("not found :: patch existing transaction")
  void testPatchTransactionNotFound() {
    BaseTransactionDTO transactionDTO = new BaseTransactionDTO();
    when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(TransactionNotFoundException.class, () -> transactionService.patchTransaction(3L, transactionDTO));
  }

  @Test
  @DisplayName("ok :: update existing transaction")
  void testDeleteTransaction() {
    doNothing().when(transactionRepository).deleteById(any());

    transactionService.deleteTransactionById(1L);

    verify(transactionRepository, times(1)).deleteById(anyLong());
  }

}