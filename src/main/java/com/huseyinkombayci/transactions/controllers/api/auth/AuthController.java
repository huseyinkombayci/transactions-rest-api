package com.huseyinkombayci.transactions.controllers.api.auth;

import com.huseyinkombayci.transactions.domains.dtos.AuthenticateUserDTO;
import com.huseyinkombayci.transactions.domains.dtos.RegisterUserDTO;
import com.huseyinkombayci.transactions.domains.dtos.RegisteredUserDTO;
import com.huseyinkombayci.transactions.domains.dtos.TokenDTO;
import com.huseyinkombayci.transactions.services.TokenService;
import com.huseyinkombayci.transactions.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

  private final UserService userService;
  private final TokenService tokenService;

  @Operation(summary = "Create new JWT token")
  @PostMapping("/token")
  @ResponseStatus(HttpStatus.CREATED)
  public TokenDTO getToken(@RequestBody @Valid AuthenticateUserDTO authDTO) {
    return tokenService.getToken(authDTO);
  }

  @Operation(summary = "Register new API user")
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public RegisteredUserDTO register(@io.swagger.v3.oas.annotations.parameters.RequestBody(content = { @Content(mediaType = "application/json", examples = @ExampleObject(value = """
      {
        "username": "JohnDoe@test.com",
        "password": "SuperSecretPassword",
        "authorities": [
          {
            "authority": "ROLE_ADMIN"
          }
        ]
      }"""))})@RequestBody @Valid RegisterUserDTO userDTO) {
    return userService.createNewUser(userDTO);
  }
}
