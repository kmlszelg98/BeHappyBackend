package com.behappy.service;

import com.behappy.api.mood.response.MoodResponse;
import com.behappy.domain.model.module.impl.MoodModule;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.therapy.Mood;

import java.time.LocalDate;

public interface MoodService {

    //CREATE
    void addTherapyStatistic(Mood mood, long therapyId);

    //READ
    MoodResponse generates(Therapy therapy, LocalDate begin, LocalDate end);
    MoodModule findMoodModule(long therapyId);


    //UPDATE

    //DELETE
}

