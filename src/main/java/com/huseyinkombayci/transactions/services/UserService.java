package com.huseyinkombayci.transactions.services;

import com.huseyinkombayci.transactions.domains.dtos.RegisterUserDTO;
import com.huseyinkombayci.transactions.domains.dtos.RegisteredUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

  RegisteredUserDTO createNewUser(RegisterUserDTO userDTO);

}
