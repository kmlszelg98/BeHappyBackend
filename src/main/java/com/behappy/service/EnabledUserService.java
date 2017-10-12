package com.behappy.service;


import com.behappy.domain.model.EnabledUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface EnabledUserService {
   EnabledUser findAndDelete(String id);
   void addUser(EnabledUser enabledUser);

   boolean exists(EnabledUser enabledUser);
}
