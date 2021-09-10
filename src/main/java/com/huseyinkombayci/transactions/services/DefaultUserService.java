package com.huseyinkombayci.transactions.services;

import com.huseyinkombayci.transactions.domains.dtos.RegisterUserDTO;
import com.huseyinkombayci.transactions.domains.dtos.RegisteredUserDTO;
import com.huseyinkombayci.transactions.domains.mappers.UserMapper;
import com.huseyinkombayci.transactions.domains.models.User;
import com.huseyinkombayci.transactions.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultUserService implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public RegisteredUserDTO createNewUser(RegisterUserDTO userDTO) {
    User user = userMapper.userDTOToUser(userDTO);
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    User savedUser = userRepository.save(user);
    return userMapper.userToUserDTO(savedUser);
  }
}
