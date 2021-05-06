package com.shop.gutenTag.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User as u  where u.email = ?1 and u.password = ?1")
    User findByUserIdAndPassword(String email, String password);

    @Query("select u from User as u  where u.id = ?1")
    User findByUserId(Long userId);

    @Query("select u from User as u  where u.email = ?1")
    User findById(String email);
}