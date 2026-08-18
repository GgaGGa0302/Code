package com.finm.account.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finm.account.auth.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
