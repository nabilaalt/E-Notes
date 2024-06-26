package com.pbo.enotes.repository;

import com.pbo.enotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByEmail(String email);
}
