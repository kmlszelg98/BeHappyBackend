package com.behappy.domain.model.therapy;

import com.behappy.domain.model.user.User;

public class Patient extends Member {

    public Patient() {
        super();
    }

    public Patient(String email, boolean active){
        super(email, active);
    }

    public static Patient fromUser(User user) {
        return new Patient(user.getEmail(), false);
    }
}
