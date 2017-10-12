package com.behappy.service;

import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.therapy.Activity;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Jarema on 11.01.2017.
 */
public interface ActivityService {
    List<Activity> getListForDay(Therapy therapy, LocalDate date);
    List<List<Activity>> getListForWeek(Therapy therapy, LocalDate startDay);
    long getSumOfMarksByDay(Therapy therapy, int year, int month, int day);
    double getAVGofMarksByMonth(Therapy therapy, int year, int month);
    double getAVGofMarksByYear(Therapy therapy, int year);
    double getAVGofMarksByRange(Therapy therapy,LocalDate begin, LocalDate end);

    long getActivityTimeInMinutesByDay(Therapy therapy, int year, int month, int day);
    double getAVGofTimeActivityByMonth(Therapy therapy,int year, int month);
    double getAVGofTimeActivityByYear(Therapy therapy,int year);
    double getAVGofTimeActivityRange(Therapy therapy,LocalDate begin, LocalDate end);

}
