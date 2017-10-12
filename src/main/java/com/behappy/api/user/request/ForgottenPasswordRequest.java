package com.behappy.api.user.request;

import com.behappy.domain.model.user.User;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

/**
 * Created by Grzegrz on 2017-01-11.
 */
public class ForgottenPasswordRequest {
    @Email
    @Size(min=4, max=50)
    private String email;

    @Size(min = 6, max = 50)
    private String newPassword;

    @Size(min = 6, max = 50)
    private String newPasswordConfirmed;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmed() {
        return newPasswordConfirmed;
    }

    public void setNewPasswordConfirmed(String newPasswordConfirmed) {
        this.newPasswordConfirmed = newPasswordConfirmed;
    }
    public User createUser(){
        if (!newPassword.equals(newPasswordConfirmed)) {
            throw new IllegalArgumentException("Passwords don't match");
        }
        return new User(email, newPassword);
    }
}
