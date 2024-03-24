package com.nebulashrine.thparty.service.serviceInterface;

import com.nebulashrine.thparty.entity.mysqlEntity.User;

public interface UserService {
    User createUser(String username, String jti);

    User addUser(User user);

    User queryUser(String username);

    User modifyUserDetails(User oldUser, User newUser);

    User joinParty(String username, int partyId);

    User leaveParty(String username, int partyId);
}
