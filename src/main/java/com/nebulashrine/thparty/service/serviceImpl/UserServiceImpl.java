package com.nebulashrine.thparty.service.serviceImpl;

import com.nebulashrine.thparty.common.exceptions.EntityCannotFoundException;
import com.nebulashrine.thparty.common.exceptions.MultipleEntitiesException;
import com.nebulashrine.thparty.common.utils.UserUtils;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.repository.UserRepo;
import com.nebulashrine.thparty.service.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    private UserUtils userUtils;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, UserUtils userUtils) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userUtils = userUtils;
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

    @Override
    public User modifyUserDetails(User oldUser, User newUser) {
        if(!oldUser.getUuid().equals(newUser.getUuid())){
            return null;
        }
        if (oldUser.getId() != newUser.getId()){
            return null;
        }
        User save = userRepo.save(newUser);
        return save;
    }

    @Override
    public User joinParty(String username, int partyId) {
        User user = userUtils.getUser();
        ArrayList<Integer> ids = new ArrayList<>();
        if (user.getParties() != null && !user.getParties().isEmpty()){
            ids = userUtils.deserializationParty(user.getParties());
        }
        if (ids.contains(partyId)){
            throw new MultipleEntitiesException("you have joined this party");
        }
        ids.add(partyId);
        String s = userUtils.serializationParty(ids);
        return userRepo.modifyParty(username, s);
    }

    @Override
    public User leaveParty(String username, int partyId) {
        User user = userUtils.getUser();
        ArrayList<Integer> ids = new ArrayList<>();
        if (user.getParties() != null && !user.getParties().isEmpty()){
            ids = userUtils.deserializationParty(user.getParties());
        }
        if (!ids.contains(partyId)){
            throw new EntityCannotFoundException("you haven't jointed this party yet");
        }
        ids.remove(partyId);
        String s = userUtils.serializationParty(ids);
        return userRepo.modifyParty(username, s);
    }
}
