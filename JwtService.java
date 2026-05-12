package com.example.docstore.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private final SecretKey key;
  private final String issuer;
  private final long accessTokenMinutes;

  public JwtService(
      @Value("${app.jwt.secret}") String base64Secret,
      @Value("${app.jwt.issuer}") String issuer,
      @Value("${app.jwt.accessTokenMinutes}") long accessTokenMinutes) {
    this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
    this.issuer = issuer;
    this.accessTokenMinutes = accessTokenMinutes;
  }

  public String issueAccessToken(String username) {
    Instant now = Instant.now();
    Instant exp = now.plusSeconds(accessTokenMinutes * 60);
    return Jwts.builder()
        .issuer(issuer)
        .subject(username)
        .issuedAt(Date.from(now))
        .expiration(Date.from(exp))
        .signWith(key)
        .compact();
  }

  public Claims parseAndValidate(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .requireIssuer(issuer)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}

