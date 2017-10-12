package com.behappy.persistence.dao.impl;

import com.behappy.domain.model.therapy.Mood;
import com.behappy.persistence.entity.MoodPersistence;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
class MoodMapper {

    List<Mood> toDomain(List<MoodPersistence> persistenceList){
        return persistenceList.stream()
                .map(this::toSingleDomain)
                .collect(Collectors.toList());
    }

    private Mood toSingleDomain(MoodPersistence persistence){
        return new Builder()
                .withId(persistence.getId())
                .withDate(persistence.getDate())
                .withMark(persistence.getMark())
                .withFear(persistence.getFear())
                .withAutomatic(persistence.getAutomatic())
                .buildDomain();
    }

    List<MoodPersistence> toPersistence(List<Mood> moodList){
        return moodList.stream()
                .map(this::toSinglePersistence)
                .collect(Collectors.toList());
    }

    private MoodPersistence toSinglePersistence(Mood mood){
        return new Builder()
                .withId(mood.getId())
                .withDate(mood.getDate())
                .withMark(mood.getMark())
                .withFear(mood.getFear())
                .withAutomatic(mood.getAutomatic())
                .buildPersistence();
    }

    private static class Builder {
        private long id;
        private LocalDate date;
        private int mark;
        private int fear;
        private String automatic;

        Builder(){}

        Builder withId(long id){
            this.id = id;
            return this;
        }

        Builder withDate(LocalDate date){
            this.date = date;
            return this;
        }

        Builder withMark(int mark){
            this.mark = mark;
            return this;
        }

        Builder withFear(int fear){
            this.fear = fear;
            return this;
        }

        Builder withAutomatic(String automatic){
            this.automatic = automatic;
            return this;
        }

        Mood buildDomain(){
            return new Mood(id, date, mark, fear, automatic);
        }

        MoodPersistence buildPersistence(){
            MoodPersistence persistence = new MoodPersistence(date, mark, fear, automatic);
            persistence.setId(id);
            return persistence;
        }
    }
}
