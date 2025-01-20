package com.mj.book_seller.repository;

import com.mj.book_seller.model.Role;
import com.mj.book_seller.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);

  @Modifying
  @Query("update UserEntity set role = :role where username = :username")
  void updateUserRole(@Param("username") String username, @Param("role") Role role);
}
