package com.mj.book_seller.security;

import com.mj.book_seller.model.Role;
import com.mj.book_seller.model.UserEntity;
import com.mj.book_seller.util.SecurityUtils;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserPrincipal implements UserDetails {

  private Long id;
  private String username;
  transient String password;
  transient UserEntity user;
  private Set<GrantedAuthority> authorities;

  public static UserPrincipal createSuperUser() {
    Set<GrantedAuthority> authoritySet = Set.of(SecurityUtils.convertToAuthority(Role.SYSTEM_MANAGER.name()));
    return UserPrincipal.builder()
        .id(-1L)
        .username("system-administrator")
        .authorities(authoritySet)
        .build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }
}
