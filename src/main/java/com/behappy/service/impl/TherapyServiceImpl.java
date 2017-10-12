package com.behappy.service.impl;

import com.behappy.api.therapy.request.TherapyRequest;
import com.behappy.domain.model.module.impl.ActivityModule;
import com.behappy.domain.model.module.impl.MoodModule;
import com.behappy.domain.model.therapy.Member;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.user.RoleEnum;
import com.behappy.domain.model.user.User;
import com.behappy.exceptions.NoSuchTherapyException;
import com.behappy.persistence.dao.impl.ActivityModuleDao;
import com.behappy.persistence.dao.impl.MoodModuleDao;
import com.behappy.persistence.dao.impl.TherapyDao;
import com.behappy.service.DiscoverService;
import com.behappy.service.TherapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TherapyServiceImpl implements TherapyService {

    private final DiscoverService discoverService;
    private final TherapyDao therapyDao;
    private final MoodModuleDao moodModuleDao;
    private final ActivityModuleDao activityModuleDao;

    @Autowired
    public TherapyServiceImpl(DiscoverService discoverService,
                              TherapyDao therapyDao, MoodModuleDao moodModuleDao, ActivityModuleDao activityModuleDao) {
        this.discoverService = discoverService;
        this.therapyDao = therapyDao;
        this.moodModuleDao = moodModuleDao;
        this.activityModuleDao = activityModuleDao;
    }

    @Override
    public Therapy create(TherapyRequest therapyRequest, User loggedUser) {
        Therapy therapy = new Therapy(loggedUser.getEmail(), therapyRequest.getName(), therapyRequest.getBeginningDate());
        therapy.addUser(loggedUser, therapyRequest.getRole());
        therapyDao.save(therapy);
        activityModuleDao.save(new ActivityModule(therapy.getId()));
        moodModuleDao.save(new MoodModule(therapy.getId()));
        //fixme added for backward compatibility if front/mobile doesnt need it then delete and change to void
        return therapyDao.find(therapy.getId());
    }

    @Override
    public boolean therapyExists(String therapyName, User loggedUser) {
        return therapyDao.findByEmail(loggedUser.getEmail())
                .stream()
                .anyMatch(t -> t.getName().equals(therapyName));
    }

    @Override
    public void activateUser(User loggedUser, String inviterMail, Therapy therapy) {
        therapy.activateUser(loggedUser.getEmail(), inviterMail);
        therapyDao.save(therapy);
    }

    @Override
    public void save(Therapy therapy) {
        therapyDao.save(therapy);
    }

    @Override
    public void assignUser(User user, Therapy therapy, RoleEnum role) {
        therapy.addUser(user, role);
        therapyDao.save(therapy);
    }

    @Override
    public Therapy getTherapy(long id) {
        return therapyDao.find(id);
    }

    @Override
    public List<Therapy> getUserTherapies(User user) {
        return therapyDao.findByEmail(user.getEmail());
    }

    @Override
    public List<Long> getUserTherapiesID(User user) {
        return getUserTherapies(user)
                .stream()
                .map(Therapy::getId)
                .collect(Collectors.toList());
    }

    @Override
    public Therapy editTherapy(Therapy therapy, TherapyRequest therapyRequest) {
        therapy.setName(therapyRequest.getName());
        therapyDao.save(therapy);
        //fixme same as higher
        return therapyDao.find(therapy.getId());
    }

    //Only patient can make warden a special user
    @Override
    public void setWardenToSpecial(Therapy therapy, User userToUpdate) {
        therapy.assignSpecial(userToUpdate.getEmail());
        therapyDao.save(therapy);
    }

    @Override
    public void removeTherapy(Therapy therapy) {
        therapyDao.delete(therapy.getId());
    }

    @Override
    public void removeUserFromTherapy(User user, Therapy therapy) {
        if(therapy.discoverRole(user) == RoleEnum.PATIENT){
            removeTherapy(therapy);
        } else {
            therapy.removeUser(user.getEmail());
            therapyDao.save(therapy);
        }
    }
}
