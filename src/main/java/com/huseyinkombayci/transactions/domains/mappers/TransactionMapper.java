package com.huseyinkombayci.transactions.domains.mappers;

import com.huseyinkombayci.transactions.domains.dtos.BaseTransactionDTO;
import com.huseyinkombayci.transactions.domains.dtos.IdentifiableTransactionDTO;
import com.huseyinkombayci.transactions.domains.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

  BaseTransactionDTO transactionToTransactionDTO(Transaction transaction);

  Transaction transactionDTOToTransaction(BaseTransactionDTO baseTransactionDTO);

  IdentifiableTransactionDTO transactionToIdentifiableTransactionDTO(Transaction transaction);
}
