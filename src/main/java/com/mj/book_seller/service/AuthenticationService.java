package com.mj.book_seller.service;

import com.mj.book_seller.model.UserEntity;

public interface AuthenticationService {
  UserEntity signInAndReturnJwt(UserEntity userRequest);
}
