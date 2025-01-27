package com.nexign.securityService.service;

import com.nexign.securityService.entity.UserEntity;
import com.nexign.securityService.repository.UserEntityRepository;
import com.nexign.securityService.security.UserDetailsClass;
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
    private final UserEntityRepository userEntityRepository;

    @Autowired
    public UserService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userEntityRepository.findByLogin(username);
        //используем кастомный UserDetails
        //return new UserDetailsClass(user.orElseThrow(() -> new UsernameNotFoundException("User not found")));
        //используем стандартный UserDetails
        return user.map(u -> new User(u.getLogin(), u.getPass(), Collections.singleton(u.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
