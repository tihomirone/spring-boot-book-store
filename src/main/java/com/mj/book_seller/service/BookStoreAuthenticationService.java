package com.mj.book_seller.service;

import com.mj.book_seller.model.UserEntity;
import com.mj.book_seller.security.UserPrincipal;
import com.mj.book_seller.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookStoreAuthenticationService implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;

  @Override
  public UserEntity signInAndReturnJwt(UserEntity userRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    userPrincipal.getUser().setToken(jwtProvider.generateToken(userPrincipal));
    return userPrincipal.getUser();
  }
}
