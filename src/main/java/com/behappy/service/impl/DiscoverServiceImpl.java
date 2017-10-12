package com.behappy.service.impl;

import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.user.User;
import com.behappy.domain.model.user.RoleEnum;
import com.behappy.exceptions.NoSuchUserException;
import com.behappy.service.DiscoverService;
import com.behappy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DiscoverServiceImpl implements DiscoverService {

    private final UserService userService;

    @Autowired
    public DiscoverServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public RoleEnum discoverRole(User user, Therapy therapy){

        if(therapy.getPatient().getMail().equals(user.getEmail())){
            return RoleEnum.PATIENT;
        }

        if(therapy.getTherapistSet().stream().anyMatch(t -> t.getMail().equals(user.getEmail()))){
            return RoleEnum.THERAPIST;
        }

        if(therapy.getWardenSet().stream().anyMatch(w -> w.getMail().equals(user.getEmail()))){
            return RoleEnum.WARDEN;
        }

        throw new NoSuchUserException("No such user in given therapy");
    }

    public User getLoggedUser() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUser(principal.getEmail());
    }
}
