package com.behappy.persistence.dao.impl;

import com.behappy.domain.model.therapy.Activity;
import com.behappy.persistence.entity.ActivityPersistence;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
class ActivityMapper {

    List<Activity> toDomain(List<ActivityPersistence> persistenceList){
        return persistenceList.stream()
                .map(this::toSingleActivity)
                .collect(Collectors.toList());
    }

    List<ActivityPersistence> toPersistence(List<Activity> list){
        return list.stream()
                .map(this::fromSingleActivity)
                .collect(Collectors.toList());
    }

    private ActivityPersistence fromSingleActivity(Activity activity){
        return new ActivityBuilder()
                .withId(activity.getId())
                .withMark(activity.getMark())
                .withActivity(activity.getActivity())
                .withDate(activity.getDate())
                .withStartTime(activity.getStartTime())
                .withEndTime(activity.getEndTime())
                .buildPersistence();
    }

    private Activity toSingleActivity(ActivityPersistence persistence){
        return new ActivityBuilder()
                .withId(persistence.getId())
                .withMark(persistence.getMark())
                .withActivity(persistence.getActivity())
                .withDate(persistence.getDate())
                .withStartTime(persistence.getStartTime())
                .withEndTime(persistence.getEndTime())
                .buildActivity();
    }

    private static class ActivityBuilder {

        private long id;
        private int mark;
        private String activity;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;

        ActivityBuilder(){}

        ActivityBuilder withId(long id){
            this.id = id;
            return this;
        }

        ActivityBuilder withMark(int mark){
            this.mark = mark;
            return this;
        }

        ActivityBuilder withActivity(String activity){
            this.activity = activity;
            return this;
        }

        ActivityBuilder withDate(LocalDate date){
            this.date = date;
            return this;
        }

        ActivityBuilder withStartTime(LocalTime startTime){
            this.startTime = startTime;
            return this;
        }

        ActivityBuilder withEndTime(LocalTime endTime){
            this.endTime = endTime;
            return this;
        }

        Activity buildActivity(){
            Activity domain = new Activity(date, mark, activity, startTime, endTime);
            domain.setId(id);
            return domain;
        }

        ActivityPersistence buildPersistence(){
            ActivityPersistence persistence = new ActivityPersistence(date, mark, activity, startTime, endTime);
            persistence.setId(id);
            return persistence;
        }
    }
}
