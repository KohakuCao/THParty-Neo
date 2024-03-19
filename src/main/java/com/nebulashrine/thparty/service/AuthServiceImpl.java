package com.nebulashrine.thparty.service;

import com.nebulashrine.thparty.common.utils.AuthUtils;
import com.nebulashrine.thparty.common.utils.JwtUtils;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtils jwtUtils;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthUtils authUtils;



    @Autowired
    public AuthServiceImpl(JwtUtils jwtUtils, UserService userService, PasswordEncoder passwordEncoder, AuthUtils authUtils) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authUtils = authUtils;
    }

    @Override
    public String loginViaTHP(String username, String ip) {
        User user = userService.queryUser(username);
        if (ObjectUtils.isEmpty(user)){
            return null;
        }

        user.setLastLoginIp(ip);
        String token = authUtils.generateToken(user);
        return token;
    }

    @Override
    public @Nullable String register(String username, String jti) {
        String password = passwordEncoder.encode(jti);
        User user = userService.createUser(username, jti);
        user.setPassword(password);
        user.setUuid(UUID.randomUUID().toString());
        user.setAuthority("USER");
        user.setRegisterDate(new Date());

        User userSaved = null;
        String token = null;
        try {
            userSaved = userService.addUser(user);
            token = authUtils.generateToken(userSaved);

        }catch (Exception e){
            e.printStackTrace();
        }

        return token;
    }
}
