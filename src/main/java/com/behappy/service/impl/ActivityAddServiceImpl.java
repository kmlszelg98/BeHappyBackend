package com.behappy.service.impl;

import com.behappy.api.activity.request.ActivityContainerRequest;
import com.behappy.api.activity.request.ActivityRequest;
import com.behappy.persistence.dao.impl.ActivityModuleDao;
import com.behappy.domain.model.module.impl.ActivityModule;
import com.behappy.domain.model.therapy.Activity;
import com.behappy.service.ActivityAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityAddServiceImpl implements ActivityAddService {

    private final ActivityModuleDao moduleDao;

    @Autowired
    public ActivityAddServiceImpl(ActivityModuleDao moduleDao) {
        this.moduleDao = moduleDao;
    }

    @Override
    public void addFromContainer(ActivityContainerRequest containerRequest, ActivityModule module) {
        List<Activity> activities = containerRequest.getActivities();
        module.addNewActivities(activities);
        moduleDao.save(module);
    }

    @Override
    public void addTherapyActivity(long therapyId, List<Activity> activityList) {
        ActivityModule module = moduleDao.find(therapyId);
        module.addNewActivities(activityList);
        moduleDao.save(module);
    }
}
