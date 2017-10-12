package com.behappy.api.user.request;

import com.behappy.domain.model.user.User;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditRequest {

    @Email
    @Size(min = 4, max = 50)
    private String newEmail;

    @NotNull
    @Size(min = 6, max = 50)
    private String currentPassword;

    @Size(min = 6, max = 50)
    private String newPassword;

    @Size(min = 6, max = 50)
    private String newPasswordConfirmed;

    private String gender;

    private String name;



    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User createUser() {
        if (newPassword != null && !newPassword.equals(newPasswordConfirmed)) {
            throw new IllegalArgumentException("Passwords don't match");
        }

        return new User(newEmail,newPassword,gender,name);
    }
}
