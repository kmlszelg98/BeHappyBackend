package com.behappy.persistence.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "moods")
public class MoodPersistence {

    @Id
    @Column(name = "mood_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "mark")
    private int mark;

    @Column(name = "fear")
    private int fear;

    @Column(name = "automatic_thoughts")
    private String automatic;

    public MoodPersistence(){}

    public MoodPersistence(LocalDate date, int mark, int fear, String automatic) {
        this.date = date;
        this.mark = mark;
        this.fear = fear;
        this.automatic = automatic;
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

    public int getFear() {
        return fear;
    }

    public void setFear(int fear) {
        this.fear = fear;
    }

    public String getAutomatic() {
        return automatic;
    }

    public void setAutomatic(String automatic) {
        this.automatic = automatic;
    }
}
