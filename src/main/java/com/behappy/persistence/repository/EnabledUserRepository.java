package com.behappy.persistence.repository;

import com.behappy.domain.model.EnabledUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EnabledUserRepository extends JpaRepository<EnabledUser, String> {
    EnabledUser findOneByMail(String mail);
}
