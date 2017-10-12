package com.behappy.domain.model.therapy;

import com.behappy.domain.model.user.User;

public class Therapist extends Member {

    public Therapist() { super(); }

    public Therapist(String email, boolean active){
        super(email, active);
    }

    public static Therapist fromUser(User user) {
        return new Therapist(user.getEmail(), false);
    }
}
