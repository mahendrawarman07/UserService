package com.example.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
public class ApplicationConfig {
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // new method
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors-> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }

   // old method
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable();
//        httpSecurity.cors().disable();
//        httpSecurity.authorizeHttpRequests(
//                authorize -> authorize.anyRequest().permitAll()
//        );
//
//        return httpSecurity.build();
//    }

//    httpSecurity.authorizeHttpRequests(
//                authorize -> authorize
//                            .requestMatchers("/signup").permitAll()
//                            .requestMatchers("/login").permitAll()
//                            .anyRequest().permitAll()
//        );
}