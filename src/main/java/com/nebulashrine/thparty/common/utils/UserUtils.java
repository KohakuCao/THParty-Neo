package com.nebulashrine.thparty.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nebulashrine.thparty.common.exceptions.AccessDeniedException;
import com.nebulashrine.thparty.common.exceptions.UserNotLoginException;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.entity.vo.UserVO;
import com.nebulashrine.thparty.repository.UserRepo;
import com.nebulashrine.thparty.service.serviceInterface.UserService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserUtils {

    private final UserRepo userRepo;

    @Autowired
    public UserUtils(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public @Nullable String getUsername(){
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getAttribute("sub");
    }

    public User getUser(){
        String username = getUsername();
        User user = userRepo.findUserByUsername(username);
        if (user == null){
            throw new UserNotLoginException();
        }
        return user;
    }

    public ArrayList<User> deserializationUser(String userJson){
        ArrayList<User> users = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
             users = mapper.readValue(userJson, new TypeReference<ArrayList<User>>() {
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public String serializationUser(ArrayList<User> users){
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public ArrayList<User> mergeUserList(ArrayList<User> list1, ArrayList<User> list2){
        ArrayList<User> newList = new ArrayList<>(list1);
        newList.removeAll(list2);
        newList.addAll(list2);
        return newList;
    }

    public User generateUser(UserVO userVO) throws AccessDeniedException {
        User user = getUser();
        if (userVO.getUuid().equals(user.getUuid())){
            throw new AccessDeniedException("Access Denied");
        }
        if (!isNullOrEmpty(userVO.getGender())) {
            user.setGender(userVO.getGender());
        }
        if (!isNullOrEmpty(userVO.getEmail())){
            user.setEmail(userVO.getEmail());
        }
        if (!isNullOrEmpty(userVO.getPhoneNumber())){
            user.setPhoneNumber(userVO.getPhoneNumber());
        }
        if (null != userVO.getBirthday()){
            user.setBirthday(userVO.getBirthday());
        }
        if (!isNullOrEmpty(userVO.getSignature())){
            user.setSignature(userVO.getSignature());
        }
        if (!isNullOrEmpty(userVO.getProfile())){
            user.setProfile(userVO.getProfile());
        }
        if (!isNullOrEmpty(userVO.getPersonalWebsite())){
            user.setPersonalWebsite(userVO.getPersonalWebsite());
        }
        if (!isNullOrEmpty(userVO.getLocation())){
            user.setLocation(userVO.getLocation());
        }
        if (!isNullOrEmpty(userVO.getQqNumber())){
            user.setQqNumber(userVO.getQqNumber());
        }
        return user;
    }

    private boolean isNullOrEmpty(String str){
        return null == str || str.isEmpty();
    }

    public ArrayList<Integer> deserializationParty(String partyJson){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Integer> list = null;
        try {
            list =  mapper.readValue(partyJson, new TypeReference<ArrayList<Integer>>() {
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public String serializationParty(ArrayList<Integer> partyId){
        String partyJson = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            partyJson = mapper.writeValueAsString(partyId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return partyJson;
    }
}
