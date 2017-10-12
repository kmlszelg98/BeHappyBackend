package com.behappy.api.user.response;

import com.behappy.domain.model.user.User;

/**
 * Created by Bartosz on 07.12.2016.
 */
public class UserResponse {

    private String email;
    private String name;
    private String gender;

    public UserResponse() {}

    private UserResponse(String email, String name, String gender) {
        this.email = email;
        this.name = name;
        this.gender = gender;
    }

    public UserResponse(User loggedUser) {
        this(loggedUser.getEmail(), loggedUser.getName(), loggedUser.getGender());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
