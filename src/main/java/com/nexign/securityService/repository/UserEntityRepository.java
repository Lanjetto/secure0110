package com.nexign.securityService.repository;


import com.nexign.securityService.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(String username);
}