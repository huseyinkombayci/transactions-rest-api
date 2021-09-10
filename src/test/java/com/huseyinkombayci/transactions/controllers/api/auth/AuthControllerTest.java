package com.huseyinkombayci.transactions.controllers.api.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huseyinkombayci.transactions.domains.dtos.AuthenticateUserDTO;
import com.huseyinkombayci.transactions.domains.dtos.BaseTransactionDTO;
import com.huseyinkombayci.transactions.domains.dtos.RegisterUserDTO;
import com.huseyinkombayci.transactions.domains.exceptions.TransactionNotFoundException;
import com.huseyinkombayci.transactions.domains.models.Authority;
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
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private final String baseAPIPath = "/api/auth";
  private final String username = "test-user@test.com";
  private final String password = "test-pass";

  @BeforeEach
  void setup() {

  }

  @Test
  @DisplayName("ok :: creating a user endpoint")
  public void testCreateTransaction() throws Exception {
    RegisterUserDTO registerUserDTO = new RegisterUserDTO(username, password, Set.of(new Authority("ROLE_USER")));
    mockMvc.perform(post(baseAPIPath + "/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerUserDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username", is(username)));
  }

  @Test
  @DisplayName("ok :: creating a token")
  public void testCreateToken() throws Exception {
    AuthenticateUserDTO authenticateUserDTO = new AuthenticateUserDTO("alan.turing@test.com", password);
    mockMvc.perform(post(baseAPIPath + "/token")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authenticateUserDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.access_token", notNullValue()))
        .andExpect(jsonPath("$.expires_in", is(600)))
        .andExpect(jsonPath("$.token_type", is("bearer")));
  }
}