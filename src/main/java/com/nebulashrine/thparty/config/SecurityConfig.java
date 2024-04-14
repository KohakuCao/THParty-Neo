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
                        requests.requestMatchers( "api/error", "/webjars/**").permitAll()
                                .requestMatchers("api/getURL").permitAll()
                                .requestMatchers("api/user/loginWithTHPassport").permitAll()
                                .requestMatchers("api/user/loginViaTHP").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("api/user/info/*").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("api/party/createParty").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("api/user/joinParty").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("api/user/changeProfile").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("api/user/leaveParty").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("api/party/deleteParty").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("api/party/modifyParty").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("api/org/**").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("api/games/**").hasAnyAuthority("SCOPE_message.read")
                                .requestMatchers("/*.html", "/js/*.js", "/*.js").permitAll()
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults());
        http.formLogin(login ->
                login.loginPage("/user/loginViaTHP"));
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }


}