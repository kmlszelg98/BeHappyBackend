package com.behappy.persistence.entity;

import com.behappy.domain.model.user.User;

import javax.persistence.*;

@Entity
@Table(name = "patients")
public class PatientPersistence {

    @Id
    @Column(name = "patient_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean active;

    // Mail of user who added Patient to therapy
    private String inviterMail;

    public PatientPersistence() {}

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail(){
        return getUser().getEmail();
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
