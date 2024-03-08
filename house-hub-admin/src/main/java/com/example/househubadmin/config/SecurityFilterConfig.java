package com.example.househubadmin.config;

import com.example.househubadmin.jwt.JwtTokenFilter;
import com.example.househubadmin.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(
                        auth -> {
                            auth
                                    .requestMatchers(
                                            "/api/v1/auth/**","/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
                                    ).permitAll()
                                    .requestMatchers(
                                            "/api/v1/building/**", "/api/v1/consumer/**","/api/v1/flat/**","/api/v1/notary/**"
                                            ).authenticated()
                                    .anyRequest().permitAll();
                        })
                .addFilterAfter(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        return httpSecurity.build();
    }
}