package com.behappy.domain.model.module.impl;

import com.behappy.domain.model.module.Module;
import com.behappy.domain.model.therapy.Mood;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoodModule extends Module {

    private List<Mood> moodList;

    public MoodModule(long therapyId){
        super(therapyId);
        this.moodList = new ArrayList<>();
    }

    public MoodModule(long id, long therapyId, boolean active){
        super(id, therapyId, active);
        this.moodList = new ArrayList<>();
    }

    public void addMood(Mood mood){
        moodList.add(mood);
    }

    public void removeMood(Mood mood){
        moodList.remove(mood);
    }

    public List<Mood> getMoodList() {
        return moodList;
    }

    public void setMoodList(List<Mood> moodList) {
        this.moodList = moodList;
    }

    public Optional<Mood> getMoodByDate(LocalDate date){
        return moodList.stream().filter(mood -> mood.getDate().equals(date)).findAny();
    }
}
