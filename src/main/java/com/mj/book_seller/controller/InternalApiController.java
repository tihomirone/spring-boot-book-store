package com.mj.book_seller.controller;

import com.mj.book_seller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/internal")
@RequiredArgsConstructor
public class InternalApiController {

  private final UserService userService;

  @PutMapping("make-admin/{username}")
  public ResponseEntity<?> makeAdmin(String username) {
    userService.makeAdmin(username);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
