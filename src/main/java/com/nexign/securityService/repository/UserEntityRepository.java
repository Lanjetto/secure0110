package com.nexign.securityService.repository;


import com.nexign.securityService.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

}