package com.behappy.api.user.request;

import org.hibernate.validator.constraints.Email;

/**
 * Created by Grzegrz on 2017-01-11.
 */
public class PasswordReminderRequest {
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
