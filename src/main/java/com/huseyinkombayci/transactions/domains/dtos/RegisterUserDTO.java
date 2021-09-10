package com.huseyinkombayci.transactions.domains.dtos;

import com.huseyinkombayci.transactions.domains.models.Authority;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO implements BaseUserDTO {

  @Email
  @NotBlank
  @Schema(description = "User username")
  private String username;

  @NotBlank
  @Schema(description = "User password")
  private String password;

  @NotEmpty
  @Schema(description = "User roles. Supported values - ROLE_ADMIN and ROLE_USER")
  private Set<Authority> authorities = new HashSet<>();

}
