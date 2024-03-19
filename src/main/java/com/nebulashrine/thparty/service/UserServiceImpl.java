package com.nebulashrine.thparty.service;

import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private UserRepo userRepo;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(String username, String jti) {
        User user = new User();
        user.setUsername(username);
        user.setAuthority("USER");
        user.setPassword(passwordEncoder.encode(jti));
        return user;
    }

    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User queryUser(String username) {
        return userRepo.findUserByUsername(username);
    }
}
