package com.behappy.api.therapy.response;

import com.behappy.domain.model.user.User;

/**
 * Created by Grzegrz on 2016-12-07.
 */
public class UserResponse {

    private long id;
    private String email;

    UserResponse(long id, String email){
        this.id=id;
        this.email=email;
    }

    public static UserResponse fromUser(User user){
        return new UserResponse(user.getId(), user.getEmail());
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
