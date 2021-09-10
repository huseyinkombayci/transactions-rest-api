package com.huseyinkombayci.transactions.domains.exceptions;

import org.springframework.http.HttpStatus;
import java.util.List;

public record ApiError(HttpStatus status, List<String> errors) {}
