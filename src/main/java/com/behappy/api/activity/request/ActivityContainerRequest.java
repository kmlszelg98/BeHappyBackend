package com.behappy.api.activity.request;

import com.behappy.domain.model.therapy.Activity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityContainerRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private List<ActivityRequest> activities;

    public ActivityContainerRequest(){}


    public ActivityContainerRequest(LocalDate date, List<ActivityRequest> activities) {
        this.date = date;
        this.activities = activities;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Activity> getActivities() {
        return activities.stream().map(request -> request.toActivity(date)).collect(Collectors.toList());
    }

    public void setActivities(List<ActivityRequest> activities) {
        this.activities = activities;
    }
}
