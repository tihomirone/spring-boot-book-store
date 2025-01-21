package com.mj.book_seller.security;

import com.mj.book_seller.util.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class InternalApiAuthenticationFilter extends OncePerRequestFilter {

  private final String accessKey;


  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return !request.getRequestURI().startsWith("/api/internal");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String requestKey = SecurityUtils.extractAuthTokenFromRequest(request);
      if (requestKey == null || !requestKey.equals(accessKey)) {
        log.warn("Internal key endpoint requested without/wrong key uri: {}", request.getRequestURI());
        throw new AuthorizationDeniedException("UNAUTHORIZED!");
      }
      UserPrincipal user = UserPrincipal.createSuperUser();
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    } catch (Exception e) {
      log.error("Could not set user authentication in security context!", e);
    }
    filterChain.doFilter(request, response);
  }
}
