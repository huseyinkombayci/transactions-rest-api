package com.huseyinkombayci.transactions.services;

import com.huseyinkombayci.transactions.domains.dtos.RegisterUserDTO;
import com.huseyinkombayci.transactions.domains.dtos.RegisteredUserDTO;
import com.huseyinkombayci.transactions.domains.mappers.UserMapper;
import com.huseyinkombayci.transactions.domains.models.Authority;
import com.huseyinkombayci.transactions.domains.models.User;
import com.huseyinkombayci.transactions.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Spy
  private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  @Spy
  private BCryptPasswordEncoder passwordEncoder;


  @Autowired
  @InjectMocks
  private DefaultUserService userService;

  private User user1;

  private final String testUsername = "test@test.com";
  private final String rawPassword = "test-pass";
  private String encodedPassword;
  private final String role = "ROLE_ADMIN";

  @BeforeEach
  public void setUp() {
    encodedPassword = passwordEncoder.encode(rawPassword);
    user1 = new User(1L, LocalDateTime.now(), LocalDateTime.now(), testUsername, encodedPassword, Boolean.TRUE, Set.of(new Authority(role)));
  }

  @Test
  @DisplayName("ok :: create new user")
  void testCreateNewTransaction() {
    RegisterUserDTO registerUserDTO = new RegisterUserDTO(testUsername, encodedPassword, Set.of(new Authority(role)));
    when(userRepository.save(any())).thenReturn(user1);

    RegisteredUserDTO registeredUserDTO = userService.createNewUser(registerUserDTO);

    assertThat(registeredUserDTO, notNullValue());
    assertThat(registeredUserDTO.getId(), is(user1.getId()));
    assertThat(registeredUserDTO.getUsername(), is(user1.getUsername()));
    verify(userRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("ok :: verify encoded password")
  void testEncodedPassword() {
    RegisterUserDTO registerUserDTO = new RegisterUserDTO(testUsername, encodedPassword, Set.of(new Authority(role)));
    when(userRepository.findById(any())).thenReturn(Optional.of(user1));

    String password = userRepository.findById(1L).map(User::getPassword).orElse("");

    assertThat(passwordEncoder.matches(rawPassword, password), is(true));
  }
}