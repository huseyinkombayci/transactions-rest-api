package com.huseyinkombayci.transactions.repositories;

import com.huseyinkombayci.transactions.domains.models.Transaction;
import com.huseyinkombayci.transactions.domains.models.TransactionStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TransactionRepositoryTest {

  @Autowired
  private TransactionRepository transactionRepository;

  private Transaction transaction;

  @BeforeEach
  public void setUp() {
    transaction = new Transaction(1L, LocalDateTime.now(), LocalDateTime.now(), TransactionStatus.CREATED, BigDecimal.valueOf(100.0), "GBP", "Test Transaction");
  }

  @AfterEach
  public void tearDown() {
    transactionRepository.deleteAll();
  }

  @Test
  @DisplayName("ok :: find transactions by status")
  public void testTransactionFindByStatus(){
    transactionRepository.save(transaction);
    List<Transaction> transactions = transactionRepository.findAllByStatus(TransactionStatus.CREATED);
    assertThat(transactions, hasItem(transaction));
  }

  @Test
  @DisplayName("no transactions found :: find transactions by status")
  public void testTransactionFindByStatusNoTransactions(){
    transactionRepository.save(transaction);
    List<Transaction> transactions = transactionRepository.findAllByStatus(TransactionStatus.PROCESSING);
    assertThat(transactions, empty());
  }
}