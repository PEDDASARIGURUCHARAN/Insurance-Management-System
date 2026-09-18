package com.aegis.auth.repository;

import com.aegis.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByEmail(String email);

    Optional<User> findByLoginIdOrEmail(String loginId, String email);

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);
}
