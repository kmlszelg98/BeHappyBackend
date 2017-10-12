package com.behappy.service;

import com.behappy.domain.model.EmailContainer;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Grzegrz on 2017-01-11.
 */
@Service
public interface EmailContainerService {
    Optional<EmailContainer> find(String id);
    Optional<EmailContainer> existsForMail(String mail);
    void delete(String id);
    void add(EmailContainer emailContainer);
}
