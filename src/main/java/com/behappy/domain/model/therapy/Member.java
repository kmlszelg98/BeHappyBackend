package com.behappy.domain.model.therapy;

public class Member {

    private long id;
    private String mail;
    private boolean active;
    // Mail of the person who added member to therapy
    private String inviterMail;

    // Name and sex will be added later

    public Member() {}

    public Member(String mail, boolean active){
        this.mail = mail;
        this.active = active;
    }

    public Member(long id, String mail, boolean active) {
        this(mail, active);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
