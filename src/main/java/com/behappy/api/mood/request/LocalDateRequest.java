package com.behappy.api.mood.request;

import java.time.LocalDate;

public class LocalDateRequest {

    private LocalDate date;

    public LocalDateRequest(){}

    public LocalDateRequest(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
