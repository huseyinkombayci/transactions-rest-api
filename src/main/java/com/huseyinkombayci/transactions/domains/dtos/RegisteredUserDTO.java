package com.huseyinkombayci.transactions.domains.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegisteredUserDTO implements IdentifiableDTO, BaseUserDTO {

  @NotBlank
  @Schema(description = "User id")
  private Long id;

  @Email
  @NotBlank
  @Schema(description = "User username")
  private String username;

}
