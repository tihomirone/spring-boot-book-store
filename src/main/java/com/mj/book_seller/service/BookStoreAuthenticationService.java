package com.mj.book_seller.service;

import com.mj.book_seller.model.UserEntity;
import com.mj.book_seller.security.UserPrincipal;
import com.mj.book_seller.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class BookStoreAuthenticationService implements AuthenticationService {

  @Autowired
  private AuthenticationManager authenticationManagerBean;
  @Autowired
  private JwtProvider jwtProvider;

  @Override
  public UserEntity signInAndReturnJwt(UserEntity userRequest) {
    Authentication authentication = authenticationManagerBean.authenticate(
        new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    userPrincipal.getUser().setToken(jwtProvider.generateToken(userPrincipal));
    return userPrincipal.getUser();
  }
}
