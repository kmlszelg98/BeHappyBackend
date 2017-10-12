package com.behappy.api.mood.response;

import java.time.LocalDate;

public class SingleMoodResponse {

    private LocalDate date;
    private int mark;
    private int fear;
    private String automatic;

    public SingleMoodResponse(LocalDate date, int mark, int fear, String automatic) {
        this.date = date;
        this.mark = mark;
        this.fear = fear;
        this.automatic = automatic;
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
