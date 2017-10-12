package com.behappy.domain.model.therapy;

import com.behappy.domain.model.user.User;

import java.time.LocalDate;

public class Warden extends Member {

    private boolean special = false;
    private LocalDate dateOfFlag;

    public Warden() { super(); }

    public Warden(String email, boolean active){
        super(email, active);
    }

    public boolean isSpecial() {
        return special;
    }

    private void setSpecial(boolean special) {
        this.special = special;
    }

    public LocalDate getDateOfFlag() {
        return dateOfFlag;
    }

    public void setDateOfFlag(LocalDate dateOfFlag) {
        this.dateOfFlag = dateOfFlag;
    }

    public void setToCommon() {
        setSpecial(false);
    }

    public void setToSpecial() {
        setSpecial(true);
    }

    public static Warden fromUser(User user) {
        return new Warden(user.getEmail(), false);
    }
}
