package com.behappy.service;

import com.behappy.api.therapy.request.TherapyRequest;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.user.User;
import com.behappy.domain.model.user.RoleEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TherapyService {

    //CREATE
    Therapy create(TherapyRequest therapyRequest,User loggedUser);
    void assignUser(User user, Therapy therapy, RoleEnum role);

    //READ
    Therapy getTherapy(long therapyId);
    List<Therapy> getUserTherapies(User user);
    List<Long> getUserTherapiesID(User user);

    //UPDATE
    Therapy editTherapy(Therapy therapy, TherapyRequest therapyRequest);
    void setWardenToSpecial(Therapy therapy, User userToUpdate);


    //DELETE | EXCEPTION -> ERROR AND NOT DELETED
    void removeTherapy(Therapy therapy);
    void removeUserFromTherapy(User user, Therapy therapy);

    //PREDICATES
    boolean therapyExists(String therapyName , User loggedUser);

    void activateUser(User loggedUser, String inviterMail, Therapy therapy);

    void save(Therapy therapy);
}
