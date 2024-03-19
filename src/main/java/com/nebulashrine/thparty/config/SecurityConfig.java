package com.nebulashrine.thparty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(requests ->
                        requests.requestMatchers( "/error", "/webjars/**").permitAll()
                                .requestMatchers("/getURL").permitAll()
                                .requestMatchers("/user/loginWithTHPassport").permitAll()
                                .requestMatchers("/user/info").hasAnyAuthority("SCOPE_message.read")
                                .anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }


}