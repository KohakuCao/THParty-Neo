package com.nebulashrine.thparty.service;

import com.nebulashrine.thparty.entity.mysqlEntity.User;

public interface UserService {
    User createUser(String username, String jti);

    User addUser(User user);

    User queryUser(String username);

    User modifyUserDetails(User oldUser, User newUser);
}
