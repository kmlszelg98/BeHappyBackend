package com.behappy.persistence.repository;

import com.behappy.domain.model.EmailContainer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Grzegrz on 2017-01-11.
 */
public interface EmailContainerRepository extends JpaRepository<EmailContainer,String> {
    EmailContainer findOneByMail(String mail);
}
