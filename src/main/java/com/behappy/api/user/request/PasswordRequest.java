package com.behappy.api.user.request;

import javax.validation.constraints.Size;

public class PasswordRequest {

    @Size(min = 6, max = 50)
    private String password;

    public PasswordRequest(){}

    public PasswordRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
