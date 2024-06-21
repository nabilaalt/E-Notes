package com.pbo.enotes.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DefaultWebSecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((requests) -> requests
          .requestMatchers(
              "/",
              "/auth",
              "/register",
              "/css/**",
              "/js/**",
              "/assets/**",
              "/ncss/**",
              "/build/**",
              "/jquery/**",
              "/nfonts/**",
              "/nimages/**",
              "/njs/**",
              "/webjars/toastr.js/**",
              "/webjars/toastr.css/**"
          ).permitAll()
          .anyRequest().authenticated()
        )
        .formLogin((form) -> form
            .loginPage("/getting-started")
            .permitAll()
        )
        .logout((logout) -> logout
            .permitAll()
        );

    return http.build();
}

 

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

}


