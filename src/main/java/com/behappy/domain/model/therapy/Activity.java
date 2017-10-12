package com.behappy.domain.model.therapy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Activity {

    private long id;
    private LocalDate date;
    private int mark;
    private String activity;
    private LocalTime startTime;
    private LocalTime endTime;

    public Activity() {}

    public Activity(LocalDate date, int mark, String activity, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.mark = mark;
        this.activity=activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMark() {
        return mark;
    }

    public LocalTime getStartTime() {return startTime;}

    public LocalTime getEndTime() {return endTime;}

    public long getPeriodInMinutes(){
        return ChronoUnit.MINUTES.between(startTime,endTime);
    }

}


