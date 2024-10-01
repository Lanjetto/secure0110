package com.nexign.securityService.service;


import com.nexign.securityService.entity.UserEntity;
import com.nexign.securityService.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserEntityRepository userRepository;

    @Autowired
    public UserService(UserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> byLogin = userRepository.findByLogin(username);
        if (byLogin.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return byLogin.map(user ->
                new User(user.getLogin(), user.getPass(), Collections.singleton(user.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


    }
}
