package com.behappy.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class EnabledUser {

    @Id
    private String id;
    private String mail;
    private String password;

    public EnabledUser(String mail, String password) {
        this.id = UUID.randomUUID().toString();
        this.mail = mail;
        this.password = password;
    }
    public EnabledUser(){}

    public String getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
}
