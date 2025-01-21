package com.mj.book_seller.service;

import com.mj.book_seller.model.Role;
import com.mj.book_seller.model.UserEntity;
import com.mj.book_seller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookStoreUserService implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserEntity createUser(UserEntity user) {
    validateUser(user);
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
    userExists(username);
    userRepository.updateUserRole(username, Role.ADMIN);
  }

  private void validateUser(UserEntity user) {
    if (!StringUtils.hasLength(user.getUsername())) {
      log.error("Signing with user with missing username!");
    }
    if (!StringUtils.hasLength(user.getPassword())) {
      log.error("Signing with user with missing password!");
    }
    if (!StringUtils.hasLength(user.getName())) {
      log.error("Signing with user with missing name!");
    }
  }

  private void userExists(String username) {
    if (!StringUtils.hasLength(username)) {
      log.error("Username is empty!");
      throw new RuntimeException("Username is empty!");
    }
    userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Username not found!"));
  }
}
