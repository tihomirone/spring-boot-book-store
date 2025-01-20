package com.mj.book_seller.service;

import com.mj.book_seller.model.UserEntity;

import java.util.Optional;

public interface UserService {

  UserEntity setUser(UserEntity user);

  Optional<UserEntity> findByUsername(String username);

  void makeAdmin(String username);
}
