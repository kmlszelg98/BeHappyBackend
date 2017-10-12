package com.behappy.service;

import com.behappy.api.user.request.LoginPassword;
import com.behappy.domain.model.user.User;
import org.springframework.util.Base64Utils;

import java.util.List;

public interface UserService {

    //CREATE
    User addUser(User user);

    //READ
    List<User> getUsers();
    User getUser(String email);
    boolean exists(String email);

    //UPDATE
    User editUser(String currentEmail, User userWithNewData) throws IllegalAccessException;

    //DELETE



    //OTHERS
    static LoginPassword decodeUserAndPassword(String credentials){
        String[] split = new String(Base64Utils.decode(credentials.getBytes())).split(":");
        return new LoginPassword(split[0], split[1]);
    }
}