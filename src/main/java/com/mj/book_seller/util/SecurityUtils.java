package com.mj.book_seller.util;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class SecurityUtils {

  public static final String ROLE_PREFIX = "ROLE_";

  public static final String AUTH_HEADER = "authorization";
  public static final String AUTH_TOKEN_TYPE = "Bearer";
  public static final String AUTH_TOKEN_PREFIX = AUTH_TOKEN_TYPE + " ";

  public static SimpleGrantedAuthority convertToAuthority(String role) {
    return new SimpleGrantedAuthority(role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX + role);
  }

  public static String extractAuthTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTH_HEADER);
    if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith(AUTH_TOKEN_PREFIX)) {
      return bearerToken.substring(AUTH_TOKEN_PREFIX.length());
    }
    return  null;
  }

  public static SecretKey getSignKey(String nonDecodedKey) {
    return Keys.hmacShaKeyFor(nonDecodedKey.getBytes(StandardCharsets.UTF_8));
  }
}
