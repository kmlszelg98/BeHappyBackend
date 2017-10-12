package com.behappy.persistence.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class ActivityPersistence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id")
    private long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "mark")
    private int mark;

    @Column(name="activity")
    private String activity;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public ActivityPersistence(){}

    public ActivityPersistence(LocalDate date,
                               int mark,
                               String activity,
                               LocalTime startTime,
                               LocalTime endTime) {
        this.date = date;
        this.mark = mark;
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
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
}
