package com.huseyinkombayci.transactions.configurations;

import com.huseyinkombayci.transactions.domains.exceptions.ApiError;
import com.huseyinkombayci.transactions.domains.exceptions.TransactionNotFoundException;
import com.huseyinkombayci.transactions.domains.exceptions.UnauthorizedException;
import com.huseyinkombayci.transactions.domains.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(TransactionNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleTransactionNotFoundException(HttpServletRequest request,TransactionNotFoundException exception) {
    return handleException(request, exception, "handleTransactionNotFoundException {}\n", HttpStatus.NOT_FOUND, exception.getMessage());
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleUserNotFoundException(HttpServletRequest request,TransactionNotFoundException exception) {
    return handleException(request, exception, "handleUserNotFoundException {}\n", HttpStatus.NOT_FOUND, exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {
    log.error("handleMethodArgumentNotValidException {}\n", request.getRequestURI(), exception);
    List<String> errors = exception.getBindingResult().getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors);
    return new ResponseEntity<>(apiError, apiError.status());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException exception) {
    log.error("handleConstraintViolationException {}\n", request.getRequestURI(), exception);
    List<String> errors = exception.getConstraintViolations()
        .stream()
        .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
        .collect(Collectors.toList());

    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors);
    return new ResponseEntity<>(apiError, apiError.status());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Object> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException exception) {
    return handleException(request, exception, "handleAccessDeniedException {}\n", HttpStatus.FORBIDDEN, "Access denied");
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Object> handleValidationException(HttpServletRequest request, ValidationException exception) {
    return handleException(request, exception, "handleValidationException {}\n", HttpStatus.BAD_REQUEST, "Validation exception");
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<Object> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException exception) {
    return handleException(request, exception, "handleMissingServletRequestParameterException {}\n", HttpStatus.BAD_REQUEST, "Missing request parameter");
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> handleInternalServerError(HttpServletRequest request, Exception exception) {
    return handleException(request, exception, "handleInternalServerError {}\n", HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred");
  }

  private ResponseEntity<Object> handleException(HttpServletRequest request, Exception exception, String errorLog, HttpStatus status, String apiMessage) {
    log.error(errorLog, request.getRequestURI(), exception);
    ApiError apiError = new ApiError(status, List.of(apiMessage));
    return new ResponseEntity<>(apiError, apiError.status());
  }
}