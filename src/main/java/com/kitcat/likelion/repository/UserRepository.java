package com.kitcat.likelion.repository;

import com.kitcat.likelion.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    User findUserByNickname(String nickname);
}
