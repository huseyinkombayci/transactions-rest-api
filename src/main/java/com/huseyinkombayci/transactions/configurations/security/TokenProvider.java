package com.huseyinkombayci.transactions.configurations.security;

import com.huseyinkombayci.transactions.domains.dtos.TokenDTO;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

  public static final String AUTH = "auth";
  //IF YOU HAVE A SECRET KEY IN PROD LIKE THIS BURN IT WITH FIRE ASAP :)
  private static final String SECRET_KEY = "43ryodcyle8w5h5FSxLstgUX9811pxHa";
  private static final long VALIDITY_IN_SECONDS = 600;
  private static final String TOKEN_TYPE = "bearer";

  public TokenDTO createToken(Authentication authentication) {
    String authorities = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime validity = now.plusSeconds(VALIDITY_IN_SECONDS);
    Date expiration = Date.from(validity.atZone(ZoneId.systemDefault()).toInstant());

    String token = Jwts.builder()
        .setSubject(authentication.getName())
        .setIssuedAt(new Date())
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .claim(AUTH, authorities)
        .compact();

    return new TokenDTO(token, VALIDITY_IN_SECONDS, TOKEN_TYPE);
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();

    List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTH).toString().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  public boolean validate(String token) {
    try {
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
      Date expiration = claimsJws.getBody().getExpiration();
      return expiration != null && !expiration.before(new Date());
    } catch (SignatureException exception) {
      log.error("Invalid JWT signature - {}", exception.getMessage());
      return false;
    } catch (MalformedJwtException exception) {
      log.error("Invalid JWT token - {}", exception.getMessage());
      return false;
    } catch (ExpiredJwtException exception) {
      log.error("Expired JWT token - {}", exception.getMessage());
      return false;
    } catch (UnsupportedJwtException exception) {
      log.error("Unsupported JWT token - {}", exception.getMessage());
      return false;
    } catch (IllegalArgumentException exception) {
      log.error("JWT claims string is empty - {}", exception.getMessage());
      return false;
    }
  }
}
