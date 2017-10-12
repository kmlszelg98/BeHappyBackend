package com.behappy.domain.model.user;

import javax.persistence.*;
import java.lang.reflect.Field;

//TODO: Change to persistence? xd
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String gender;

    private String name;

    protected User() {
    }

   

    public User(String email, String password, String gender, String name) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.name = name;
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProperties(User anotherUser) throws IllegalAccessException{
        Field[] fields = anotherUser.getClass().getDeclaredFields();

        for(Field field:fields){
            if (!field.isAnnotationPresent(Id.class)&&!field.get(anotherUser).toString().isEmpty()&&field.get(anotherUser)!=null){
                field.set(this,field.get(anotherUser));
            }
        }
    }
}
