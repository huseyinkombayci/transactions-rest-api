package com.huseyinkombayci.transactions.controllers.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huseyinkombayci.transactions.domains.dtos.BaseTransactionDTO;
import com.huseyinkombayci.transactions.domains.exceptions.TransactionNotFoundException;
import com.huseyinkombayci.transactions.domains.models.TransactionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = {"ROLE_ADMIN"})
class TransactionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private BaseTransactionDTO transaction;
  private final String baseAPIPath = "/api/v1/transactions";

  @BeforeEach
  void setup() {
    transaction = new BaseTransactionDTO(TransactionStatus.CREATED, BigDecimal.TEN, "GBP", "Test description");
  }

  @Test
  @DisplayName("ok :: get all transactions endpoint")
  public void testGetAllTransactions() throws Exception {
    mockMvc.perform(get(baseAPIPath)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  @DisplayName("ok :: get all transactions by status endpoint")
  public void testGetAllTransactionsByStatus() throws Exception {
    mockMvc.perform(get(baseAPIPath)
            .requestAttr("status", TransactionStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].amount", is(25.60)));
  }

  @Test
  @DisplayName("ok :: get transaction by id endpoint")
  public void testGetTransactionById() throws Exception {
    mockMvc.perform(get(baseAPIPath + "/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.currency", is("GBP")));
  }

  @Test
  @DisplayName("ok :: creating a transaction endpoint")
  public void testCreateTransaction() throws Exception {
    mockMvc.perform(post(baseAPIPath)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transaction)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.amount", is(10)));
  }

  @Test
  @DisplayName("ok :: update existing transaction endpoint")
  public void testUpdateTransaction() throws Exception {
    transaction.setStatus(TransactionStatus.PROCESSING);
    transaction.setAmount(BigDecimal.ONE);
    mockMvc.perform(put(baseAPIPath + "/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transaction)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.amount", is(1)))
        .andExpect(jsonPath("$.status", is("PROCESSING")));
  }

  @Test
  @DisplayName("ok :: patch existing transaction endpoint")
  public void testPatchTransaction() throws Exception {
    transaction.setAmount(BigDecimal.ONE);
    mockMvc.perform(patch(baseAPIPath + "/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transaction)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.amount", is(1)))
        .andExpect(jsonPath("$.status", is("CREATED")));
  }

  @Test
  @WithAnonymousUser
  @DisplayName("unauthorized :: test unauthorized access")
  public void testUnauthorizedAccess() throws Exception {
    mockMvc.perform(get(baseAPIPath)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status", is("UNAUTHORIZED")));
  }
}