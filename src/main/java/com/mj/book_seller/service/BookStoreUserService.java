package com.mj.book_seller.service;

import com.mj.book_seller.model.Role;
import com.mj.book_seller.model.UserEntity;
import com.mj.book_seller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookStoreUserService implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserEntity setUser(UserEntity user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(Role.USER);
    user.setCreateTime(LocalDateTime.now());
    return userRepository.save(user);
  }

  @Override
  public Optional<UserEntity> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  @Transactional
  public void makeAdmin(String username) {
    userRepository.updateUserRole(username, Role.ADMIN);
  }
}
