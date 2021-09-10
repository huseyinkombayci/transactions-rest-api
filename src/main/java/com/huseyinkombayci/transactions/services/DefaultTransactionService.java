package com.huseyinkombayci.transactions.services;

import com.huseyinkombayci.transactions.domains.dtos.BaseTransactionDTO;
import com.huseyinkombayci.transactions.domains.dtos.IdentifiableTransactionDTO;
import com.huseyinkombayci.transactions.domains.exceptions.TransactionNotFoundException;
import com.huseyinkombayci.transactions.domains.mappers.TransactionMapper;
import com.huseyinkombayci.transactions.domains.models.Transaction;
import com.huseyinkombayci.transactions.domains.models.TransactionStatus;
import com.huseyinkombayci.transactions.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultTransactionService implements TransactionService {

  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;

  public List<IdentifiableTransactionDTO> getAllTransactions() {
    return transactionRepository.findAll()
        .stream()
        .map(transactionMapper::transactionToIdentifiableTransactionDTO)
        .collect(Collectors.toList());
  }

  public List<IdentifiableTransactionDTO> getAllTransactionsByStatus(TransactionStatus status) {
    return transactionRepository.findAllByStatus(status)
        .stream()
        .map(transactionMapper::transactionToIdentifiableTransactionDTO)
        .collect(Collectors.toList());
  }

  public IdentifiableTransactionDTO getTransactionById(Long id) {
    return transactionRepository.findById(id)
        .map(transactionMapper::transactionToIdentifiableTransactionDTO)
        .orElseThrow(() -> new TransactionNotFoundException(
            String.format("Transaction with ID: %d not found.", id)
        ));
  }

  @Override
  public IdentifiableTransactionDTO createNewTransaction(BaseTransactionDTO baseTransactionDTO) {
    Transaction transaction = transactionMapper.transactionDTOToTransaction(baseTransactionDTO);
    Transaction savedTransaction = transactionRepository.save(transaction);
    return transactionMapper.transactionToIdentifiableTransactionDTO(savedTransaction);
  }

  @Override
  public BaseTransactionDTO updateTransaction(Long id, BaseTransactionDTO baseTransactionDTO) {
    Transaction transaction = transactionMapper.transactionDTOToTransaction(baseTransactionDTO);
    transaction.setId(id);
    Transaction savedTransaction = transactionRepository.save(transaction);
    return transactionMapper.transactionToTransactionDTO(savedTransaction);
  }

  @Override
  public BaseTransactionDTO patchTransaction(Long id, BaseTransactionDTO baseTransactionDTO) {
    return transactionRepository.findById(id).map(transaction -> {
      if (baseTransactionDTO.getAmount() != null) {
        transaction.setAmount(baseTransactionDTO.getAmount());
      }

      if (baseTransactionDTO.getStatus() != null) {
        transaction.setStatus(baseTransactionDTO.getStatus());
      }

      if (baseTransactionDTO.getCurrency() != null) {
        transaction.setCurrency(baseTransactionDTO.getCurrency());
      }

      Transaction savedTransaction = transactionRepository.save(transaction);
      return transactionMapper.transactionToTransactionDTO(savedTransaction);

    }).orElseThrow(() -> new TransactionNotFoundException(
        String.format("Transaction with ID: %d not found.", id)
    ));
  }

  @Override
  public void deleteTransactionById(Long id) {
    transactionRepository.deleteById(id);
  }
}
