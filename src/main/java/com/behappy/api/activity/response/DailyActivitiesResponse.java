package com.behappy.api.activity.response;

import com.behappy.domain.model.therapy.Activity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DailyActivitiesResponse {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private List<ActivityResponse> activities = new ArrayList<>();

    public DailyActivitiesResponse(){};

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setActivities(List<ActivityResponse> activities) {
        this.activities = activities;
    }

    public DailyActivitiesResponse(LocalDate date, List<Activity> therapyActivities){
        this.date = date;
        activities.addAll(therapyActivities.stream().map(ActivityResponse::new).collect(Collectors.toList()));
    }

    public LocalDate getDate() {
        return date;
    }

    public List<ActivityResponse> getActivities() {
        return activities;
    }
}
