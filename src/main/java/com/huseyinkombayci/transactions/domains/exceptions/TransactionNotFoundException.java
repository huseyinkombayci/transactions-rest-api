package com.huseyinkombayci.transactions.domains.exceptions;

public class TransactionNotFoundException extends RuntimeException {

  public TransactionNotFoundException(String message) {
    super(message);
  }
}
