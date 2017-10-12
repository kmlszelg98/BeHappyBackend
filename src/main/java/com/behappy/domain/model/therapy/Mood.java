package com.behappy.domain.model.therapy;

import java.time.LocalDate;

public class Mood {

    private long id;
    private LocalDate date;
    private int mark;
    private int fear;
    private String automatic;

    public Mood() {}

    public Mood(LocalDate date, int mark, int fear, String automatic) {
        this.date = date;
        this.mark = mark;
        this.fear = fear;
        this.automatic = automatic;
    }

    public Mood(long id,  LocalDate date, int mark, int fear, String automatic) {
        this(date,mark,fear, automatic);
        this.id = id;
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
