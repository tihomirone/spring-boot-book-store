package com.mj.book_seller.security.jwt;

import com.mj.book_seller.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtProvider {

  String generateToken(UserPrincipal user);

  Authentication getAuthentication(HttpServletRequest request);
}
