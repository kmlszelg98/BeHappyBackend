package com.behappy.api.activity.request;

import com.behappy.domain.model.therapy.Activity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public class ActivityRequest {

    @Size(min = 1, max = 200)
    private String activity;

    @JsonFormat(pattern ="HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern ="HH:mm")
    private LocalTime endTime;

    private int mark;

    public ActivityRequest(){}

    public ActivityRequest(String activity, LocalTime startTime, LocalTime endTime, int mark) {
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mark = mark;
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

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Activity toActivity(LocalDate activityDate) {
        return new Activity(activityDate, mark, activity, startTime, endTime);
    }
}
