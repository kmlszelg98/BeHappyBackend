package com.behappy.api.mood.response;

import java.util.List;

/**
 * Created by Jarema on 12.02.2017.
 */
public class MoodResponse {

    public enum Option {
        Days,
        Weeks,
        Month,
        Years
    }

    private List<Double> moods;
    private List<Double> fears;
    private int length;
    private Option option;

    public List<Double> getMoods() {
        return moods;
    }

    public List<Double> getFears() {
        return fears;
    }

    public int getLength() {
        return length;
    }

    public Option getOption() {
        return option;
    }

    public MoodResponse(List<Double> moods, List<Double> fears, Option option) {
        this.moods = moods;
        this.fears = fears;
        this.option = option;
        if(moods.size() != fears.size())
            throw new IllegalArgumentException();
        this.length = moods.size();
    }
}
