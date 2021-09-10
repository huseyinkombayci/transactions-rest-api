package com.huseyinkombayci.transactions.repositories;

import com.huseyinkombayci.transactions.domains.models.Transaction;
import com.huseyinkombayci.transactions.domains.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
}
