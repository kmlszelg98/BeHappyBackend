package com.behappy.service.impl;

import com.behappy.domain.model.EnabledUser;
import com.behappy.persistence.repository.EnabledUserRepository;
import com.behappy.exceptions.NoSuchUserException;
import com.behappy.service.EnabledUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
public class EnabledUserServiceImpl implements EnabledUserService{

    private EnabledUserRepository enabledUserRepository;
    @Autowired
    public EnabledUserServiceImpl (EnabledUserRepository enabledUserRepository){
        this.enabledUserRepository=enabledUserRepository;
    }

    @Override
    public EnabledUser findAndDelete(String id){
        EnabledUser enabledUser = enabledUserRepository.findOne(id);
        if(enabledUser == null) {
            throw new NoSuchUserException("User not found");
        }
        enabledUserRepository.delete(id);
        return enabledUser;
    }

    //TODO: Possibly change exception, so controller returns CONFLICT instead of BAD_REQUEST
    @Override
    public void addUser(EnabledUser enabledUser){
        if(exists(enabledUser)) {
            throw new DataIntegrityViolationException("User already registered");
        }
        enabledUserRepository.save(enabledUser);
    }

    @Override
    public boolean exists(EnabledUser enabledUser) {
        return enabledUserRepository.findOneByMail(enabledUser.getMail()) != null;
    }
}

