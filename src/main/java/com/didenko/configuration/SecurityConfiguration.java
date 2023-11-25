package com.didenko.configuration;

import com.didenko.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_REQUESTS = {
            "/swagger-ui/**","/api/v1/**", "/logout", "/registration",
            "/v3/api-docs/**", "/v3/api-docs", "/swagger/resources",
            "/swagger/resources/**", "swagger-ui", "/api/auth/**",
            "/swagger-ui/index.html", "/swagger-ui.html",
            "/login", "/login/**", "/home", "/css/**", "/images/**"    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .requestMatchers(WHITE_LIST_REQUESTS).permitAll()
                        .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.getAuthority())
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/user"))
                .logout(logout -> logout.logoutSuccessUrl("/home"))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
