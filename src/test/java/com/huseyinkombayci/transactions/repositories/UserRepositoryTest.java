package com.huseyinkombayci.transactions.repositories;

import com.huseyinkombayci.transactions.domains.models.Authority;
import com.huseyinkombayci.transactions.domains.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  private User user;

  private final String testUsername = "test@test.com";

  @BeforeEach
  public void setUp() {
    String password = "test-pass";
    String role = "ROLE_ADMIN";
    user = new User(1L, LocalDateTime.now(), LocalDateTime.now(), testUsername, password, Boolean.TRUE, Set.of(new Authority(role)));
  }

  @AfterEach
  public void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("ok :: find user by username")
  void testUserFindByUsername(){
    userRepository.save(user);
    Optional<User> user = userRepository.findByUsername(testUsername);
    String username = user.map(User::getUsername).orElse("");
    assertThat(user, notNullValue());
    assertThat(username, is(testUsername));
  }

  @Test
  @DisplayName("no user found :: find user by username")
  void testUserFindByUsernameNoUser(){
    userRepository.save(user);
    Optional<User> user = userRepository.findByUsername(testUsername);
    assertThat(user.isPresent(), is(true));
  }

}