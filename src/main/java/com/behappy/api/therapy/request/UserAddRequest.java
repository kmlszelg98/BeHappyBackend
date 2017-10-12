package com.behappy.api.therapy.request;

import com.behappy.domain.model.user.RoleEnum;

import javax.validation.constraints.NotNull;

public class UserAddRequest {

    @NotNull
    public String email;
    @NotNull
    public RoleEnum role;

    public UserAddRequest(){}

    public String getEmail() {
        return email;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
