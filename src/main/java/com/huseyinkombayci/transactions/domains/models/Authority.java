package com.huseyinkombayci.transactions.domains.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority {

  private String authority;

  public enum Role {
    ADMIN(Constants.ADMIN),
    USER(Constants.USER);

    Role(String label) {
    }

    public static class Constants {
      public static final String ADMIN = "ROLE_ADMIN";
      public static final String USER = "ROLE_USER";
    }
  }
}