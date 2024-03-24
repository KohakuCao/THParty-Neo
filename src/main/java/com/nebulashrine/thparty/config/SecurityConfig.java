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
                                .requestMatchers("/user/info/*").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("/party/createParty").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("/user/joinParty").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("/user/changeProfile").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("/user/leaveParty").hasAnyAuthority("SCOPE_message.read")
                                .anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults());
        http.formLogin(login ->
                login.loginPage("/user/loginViaTHP"));
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }


}