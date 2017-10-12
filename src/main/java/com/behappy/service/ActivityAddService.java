package com.behappy.service;

import com.behappy.api.activity.request.ActivityContainerRequest;
import com.behappy.domain.model.module.impl.ActivityModule;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.therapy.Activity;

import java.util.List;

//TODO create one service with activity service or create facade, for now its
public interface ActivityAddService {
    void addFromContainer(ActivityContainerRequest containerRequest, ActivityModule module);
    void addTherapyActivity(long therapyId, List<Activity> activityList);
}
