package com.behappy.api.therapy.response;

import com.behappy.domain.model.therapy.Therapy;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class TherapyResponse {

    private long id;
    private String name;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime beginningDate;

    private int numOfPatients;
    private int numOfWardens;
    private int numOfTherapists;

    private TherapyResponse(long id, String name, LocalDateTime beginningDate, int numOfPatients, int numOfWardens, int numOfTherapists) {
        this.id = id;
        this.name = name;
        this.beginningDate = beginningDate;
        this.numOfPatients = numOfPatients;
        this.numOfWardens = numOfWardens;
        this.numOfTherapists = numOfTherapists;
    }

    public static TherapyResponse fromTherapy(Therapy therapy) {
        return new TherapyResponse(therapy.getId(), therapy.getName(), therapy.getBeginningDate(), getNum(therapy),
                                   therapy.getNumOfWardens(), therapy.getNumOfTherapist());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getBeginningDate() {
        return beginningDate;
    }

    public int getNumOfPatients() {
        return numOfPatients;
    }

    public int getNumOfWardens() {
        return numOfWardens;
    }

    public int getNumOfTherapists() {
        return numOfTherapists;
    }

    private static int getNum(Therapy therapy) {
        if (therapy.getPatient() == null){
            return 0;
        } else {
            return 1;
        }
    }
}
