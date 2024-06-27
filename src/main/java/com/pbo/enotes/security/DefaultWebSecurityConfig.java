package com.pbo.enotes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.pbo.enotes.service.UserService;

@Configuration
@EnableWebSecurity
public class DefaultWebSecurityConfig {

    private final UserService userService;

    public DefaultWebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                    "/**",
                    "/auth",
                    "/register",
                    "/css/**",
                    "/js/**",
                    "/assets/**",
                    "/ncss/**",
                    "/build/**",
                    "/jquery/**",
                    "/login",
                    "/tasks/",
                    "/static/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            ); // Disable CSRF for testing purposes; enable it in production with proper configuration

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
