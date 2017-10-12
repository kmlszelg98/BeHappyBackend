package com.behappy.service.impl;

import com.behappy.domain.model.EmailContainer;
import com.behappy.persistence.repository.EmailContainerRepository;
import com.behappy.service.EmailContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Grzegrz on 2017-01-11.
 */
@Service
public class EmailContainerServiceImpl implements EmailContainerService {
    private EmailContainerRepository emailContainerRepository;
    @Autowired
    public EmailContainerServiceImpl(EmailContainerRepository emailContainerRepository){
        this.emailContainerRepository=emailContainerRepository;
    }
    @Override
    public Optional<EmailContainer> find(String id){
        return Optional.ofNullable(emailContainerRepository.findOne(id));
    }
    @Override
    public Optional<EmailContainer> existsForMail(String mail){
        return Optional.ofNullable(emailContainerRepository.findOneByMail(mail));
    }
    @Override
    public void delete(String id){
        emailContainerRepository.delete(id);
    }
    @Override
    public void add(EmailContainer emailContainer){
        if(existsForMail(emailContainer.getEmail()).isPresent()){
            throw new IllegalArgumentException("User already sent request to change his email!");
        }
        emailContainerRepository.save(emailContainer);
    }
}
