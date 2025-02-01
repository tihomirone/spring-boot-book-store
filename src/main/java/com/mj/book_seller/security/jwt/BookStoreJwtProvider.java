package com.mj.book_seller.security.jwt;

import com.mj.book_seller.security.UserPrincipal;
import com.mj.book_seller.util.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BookStoreJwtProvider implements JwtProvider {

  @Value("${app.jwt.secret}")
  private String JWT_SECRET;

  @Value("${app.jwt.expiration-in-ms}")
  private Long JWT_EXPIRATION;

  @Override
  public String generateToken(UserPrincipal user) {
    String authorities = user.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining());

    return Jwts.builder()
        .claims()
          .subject(user.getUsername())
          .add("roles", authorities)
          .add("userId", user.getId())
          .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
        .and()
        .signWith(SecurityUtils.getSignKey(JWT_SECRET), Jwts.SIG.HS512)
        .compact();
  }

  @Override
  public Authentication getAuthentication(HttpServletRequest request) {
    Claims claims = null;
    try {
      claims = extractClaims(request);
    } catch (Exception e) {
      log.warn("Unable to extract claims from request!");
    }
    if (claims == null) {
      return null;
    }
    String username = claims.getSubject();
    Long userId = claims.get("userId", Long.class);
    Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
        .map(SecurityUtils::convertToAuthority)
        .collect(Collectors.toSet());
    UserDetails userDetails = UserPrincipal.builder()
        .username(username)
        .authorities(authorities)
        .id(userId)
        .build();
    if (username == null) {
      return null;
    }
    return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
  }

  @Override
  public boolean validateToken(HttpServletRequest request) {
    Claims claims = extractClaims(request);
    if (claims == null) {
      return false;
    }
    return !claims.getExpiration().before(new Date());
  }

  private Claims extractClaims(HttpServletRequest request) {
    String token = SecurityUtils.extractAuthTokenFromRequest(request);
    if (token == null) {
      return null;
    }

    //return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    return Jwts.parser()
        .verifyWith(SecurityUtils.getSignKey(JWT_SECRET))
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}
