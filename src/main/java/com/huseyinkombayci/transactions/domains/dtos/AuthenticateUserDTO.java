package com.huseyinkombayci.transactions.domains.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateUserDTO implements BaseUserDTO {

  @Email
  @NotBlank
  @Schema(description = "User's username")
  private String username;

  @NotBlank
  @Schema(description = "User's password")
  private String password;

}
