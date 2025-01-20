package com.mj.book_seller.security;

import com.mj.book_seller.model.UserEntity;
import com.mj.book_seller.service.UserService;
import com.mj.book_seller.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.getRole().name()));

    return UserPrincipal.builder()
        .id(user.getId())
        .user(user)
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities(authorities)
        .build();
  }
}
