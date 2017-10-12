package com.behappy.api.mood.request;

import com.behappy.domain.model.therapy.Mood;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class MoodRequest {

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    private int mark;

    @NotNull
    private int fear;

    private String automatic;

    public MoodRequest() {
    }

    public MoodRequest(LocalDate date, int mark, int fear, String automatic) {
        this.date  = date;
        this.mark  = mark;
        this.fear = fear;
        this.automatic = automatic;
    }

    public MoodRequest(Mood mood) {
        this.date = mood.getDate();
        this.mark = mood.getMark();
        this.fear = mood.getFear();
        this.automatic = mood.getAutomatic();
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
