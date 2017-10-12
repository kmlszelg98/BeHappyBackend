package com.behappy.api.activity.request;

import com.behappy.domain.model.therapy.Activity;

import java.time.LocalDate;
import java.time.LocalTime;

public class ActivityBuilder {

    private LocalDate date;
    private int mark;
    private String activity;
    private LocalTime startTime;
    private LocalTime endTime;


    public ActivityBuilder(){}

    public ActivityBuilder withDate(LocalDate date){
        this.date = date;
        return this;
    }

    public ActivityBuilder withMark(int mark){
        this.mark = mark;
        return this;
    }

    public ActivityBuilder withActivity(String activity){
        this.activity = activity;
        return this;
    }

    public ActivityBuilder withStartTime(LocalTime startTime){
        this.startTime = startTime;
        return this;
    }

    public ActivityBuilder withEndTime(LocalTime endTime){
        this.endTime = endTime;
        return this;
    }

    public Activity build() {
        checkNulls();
        return new Activity(date, mark, activity, startTime, endTime);
    }

    private void checkNulls(){
        if (date == null){
            this.date = LocalDate.now();
        } else if(activity == null){
            this.activity = "Undefined";
        } else if(startTime == null){
            this.startTime = LocalTime.now().minusHours(1);
        } else if(endTime == null){
            this.endTime = LocalTime.now();
        }
    }
}

