package com.huseyinkombayci.transactions.services;

import com.huseyinkombayci.transactions.domains.dtos.AuthenticateUserDTO;
import com.huseyinkombayci.transactions.domains.dtos.TokenDTO;

public interface TokenService {

  TokenDTO getToken(AuthenticateUserDTO authDTO);
}
