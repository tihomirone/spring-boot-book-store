package com.mj.book_seller.controller;

import com.mj.book_seller.model.UserEntity;
import com.mj.book_seller.service.AuthenticationService;
import com.mj.book_seller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService bookStoreAuthenticationService;
  private final UserService userService;

  @PostMapping("signup")
  public ResponseEntity<UserEntity> signUp(@RequestBody UserEntity user) {
    userService.createUser(user);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @PostMapping("signin")
  public ResponseEntity<UserEntity> signIn(@RequestBody UserEntity user) {
    return new ResponseEntity<>(bookStoreAuthenticationService.signInAndReturnJwt(user), HttpStatus.OK);
  }
}
