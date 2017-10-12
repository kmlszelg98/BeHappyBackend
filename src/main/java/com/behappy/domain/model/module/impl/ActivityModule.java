package com.behappy.domain.model.module.impl;

import com.behappy.domain.model.module.Module;
import com.behappy.domain.model.therapy.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityModule extends Module {

    private List<Activity> activityList;

    public ActivityModule(){}

    public ActivityModule(long therapyId){
        super(therapyId);
        this.activityList = new ArrayList<>();
    }

    public ActivityModule(long id, long therapyId, boolean active){
        super(id, therapyId, active);
        this.activityList = new ArrayList<>();
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    public void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public void addNewActivities(List<Activity> activities){
        activityList.addAll(activities);
    }
}
