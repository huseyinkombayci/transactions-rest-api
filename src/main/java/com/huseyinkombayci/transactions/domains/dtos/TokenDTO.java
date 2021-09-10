package com.huseyinkombayci.transactions.domains.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenDTO {

  @JsonProperty("access_token")
  @Schema(description = "Access token")
  private String token;

  @JsonProperty("expires_in")
  @Schema(description = "Expiry time in seconds")
  private long expires;

  @JsonProperty("token_type")
  @Schema(description = "Access token type")
  private String type;

}
