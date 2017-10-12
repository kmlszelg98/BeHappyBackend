package com.behappy.persistence.entity;

import com.behappy.domain.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "wardens")
public class WardenPersistence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean active;

    private LocalDate dateOfFlag;

    private boolean special = false;

    // Mail of user who added Patient to therapy
    private String inviterMail;

    public WardenPersistence() {}

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDateOfFlag() {
        return dateOfFlag;
    }

    public void setDateOfFlag(LocalDate dateOfFlag){
        this.dateOfFlag = dateOfFlag;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special){
        this.special = special;
    }

    public String getEmail() {
        return getUser().getEmail();
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getInviterMail() {
        return inviterMail;
    }

    public void setInviterMail(String inviterMail) {
        this.inviterMail = inviterMail;
    }
}
