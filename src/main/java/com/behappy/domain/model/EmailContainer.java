package com.behappy.domain.model;

import javax.persistence.*;

import java.util.UUID;

/**
 * Created by Grzegrz on 2017-01-11.
 */
@Entity
public class EmailContainer {

    @Id
    private String id;
    private String mail;

    public EmailContainer(String mail){
        this.mail = mail;
        id = UUID.randomUUID().toString();
    }

    public EmailContainer(){}

    public String getEmail() {
        return mail;
    }

    public void setEmail(String mail) {
        this.mail = mail;
    }

    public String getId() {
        return id;
    }
}
