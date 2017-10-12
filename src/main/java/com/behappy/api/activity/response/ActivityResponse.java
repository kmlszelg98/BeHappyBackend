package com.behappy.api.activity.response;

import com.behappy.domain.model.therapy.Activity;

import java.time.LocalTime;

/**
 * Created by kborowiec on 2017-01-11.
 */

public class ActivityResponse {

    private String activity;
    private LocalTime startTime;
    private LocalTime endTime;
    private int effort;

    public ActivityResponse(String activity, LocalTime startTime, LocalTime endTime, int effort) {
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.effort = effort;
    }

    public ActivityResponse(Activity activity){
        this.activity = activity.getActivity();
        this.startTime = activity.getStartTime();
        this.endTime = activity.getEndTime();
        this.effort = activity.getMark();
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }
}
