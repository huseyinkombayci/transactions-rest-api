package com.huseyinkombayci.transactions.services;

import com.huseyinkombayci.transactions.configurations.security.TokenProvider;
import com.huseyinkombayci.transactions.domains.dtos.AuthenticateUserDTO;
import com.huseyinkombayci.transactions.domains.dtos.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultTokenService implements TokenService {

  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;

  @Override
  public TokenDTO getToken(AuthenticateUserDTO userDTO) {
    String username = userDTO.getUsername();
    String password = userDTO.getPassword();
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    Authentication authentication = authenticationManager.authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return tokenProvider.createToken(authentication);
  }
}
