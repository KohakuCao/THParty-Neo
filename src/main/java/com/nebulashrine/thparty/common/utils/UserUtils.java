package com.nebulashrine.thparty.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.service.UserService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserUtils {

    private final UserService userService;

    @Autowired
    public UserUtils(UserService userService) {
        this.userService = userService;
    }

    public @Nullable String getUsername(){
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getAttribute("sub");
    }

    public User getUser(){
        String username = getUsername();
        User user = userService.queryUser(username);
        if (user == null){
            throw new NullPointerException();
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
}
