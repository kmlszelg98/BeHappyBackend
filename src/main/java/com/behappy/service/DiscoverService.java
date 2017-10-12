package com.behappy.service;

import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.user.User;
import com.behappy.domain.model.user.RoleEnum;

public interface DiscoverService {
    RoleEnum discoverRole(User user, Therapy therapy);
    User getLoggedUser();
}
