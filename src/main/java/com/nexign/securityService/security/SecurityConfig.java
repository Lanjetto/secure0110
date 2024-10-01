package com.nexign.securityService.security;

import com.nexign.securityService.security.filter.OwrFilter;
import com.nexign.securityService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final OwrFilter owrFilter;

    @Autowired
    public SecurityConfig(OwrFilter owrFilter) {
        this.owrFilter = owrFilter;
    }

    //    private final UserService userService;
//
//    @Autowired
//    public SecurityConfig(UserService userService) {
//        this.userService = userService;
//    }
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        return new AuthProvider(userService);
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.logout(logout -> logout
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutUrl("/rest/admin-ui/userEntities/logout")
                .permitAll());


        return http
//               .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.DELETE, "/rest/admin-ui/userEntities/*").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/rest/admin-ui/userEntities").permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(owrFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
