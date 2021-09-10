package com.huseyinkombayci.transactions.services;

import com.huseyinkombayci.transactions.domains.dtos.BaseTransactionDTO;
import com.huseyinkombayci.transactions.domains.dtos.IdentifiableTransactionDTO;
import com.huseyinkombayci.transactions.domains.models.TransactionStatus;

import java.util.List;

public interface TransactionService {

  List<IdentifiableTransactionDTO> getAllTransactions();

  List<IdentifiableTransactionDTO> getAllTransactionsByStatus(TransactionStatus status);

  IdentifiableTransactionDTO getTransactionById(Long id);

  IdentifiableTransactionDTO createNewTransaction(BaseTransactionDTO baseTransactionDTO);

  BaseTransactionDTO updateTransaction(Long id, BaseTransactionDTO baseTransactionDTO);

  BaseTransactionDTO patchTransaction(Long id, BaseTransactionDTO baseTransactionDTO);

  void deleteTransactionById(Long id);
}
