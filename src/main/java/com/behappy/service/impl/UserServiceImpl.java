package com.behappy.service.impl;

import com.behappy.domain.model.user.User;
import com.behappy.exceptions.EmailTakenException;
import com.behappy.exceptions.NoSuchUserException;
import com.behappy.persistence.repository.UserRepository;
import com.behappy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String email) {
        return userRepository.findOneByEmail(email)
                .orElseThrow(() -> new NoSuchUserException("User not found"));
    }

    @Override
    public User addUser(User user) throws DataIntegrityViolationException {
        return userRepository.save(user);
    }

    @Override
    public User editUser(String currentEmail, User userWithNewData) throws IllegalAccessException {
        String email = userWithNewData.getEmail();
        User user = getUser(currentEmail);

            if(userRepository.findOneByEmail(email).isPresent()){
                throw new EmailTakenException("Current email has been already taken");
            }
        user.setProperties(userWithNewData);
        userRepository.save(user);
        return user;
    }
    @Override
    public boolean exists(String email) {
        return userRepository.findOneByEmail(email).isPresent();
    }
}