package com.huseyinkombayci.transactions.repositories;

import com.huseyinkombayci.transactions.domains.models.Transaction;
import com.huseyinkombayci.transactions.domains.models.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

  List<Transaction> findAllByStatus(TransactionStatus status);
}
