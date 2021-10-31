package com.shop.gutenTag.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User as u  where u.email = ?1 and u.password = ?2")
    User findByUserIdAndPassword(String email, String password);

    @Query("select u from User as u  where u.email = ?1")
    User findByEmail(String email);
}
