package com.behappy.service.impl;

import com.behappy.domain.model.therapy.Activity;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.persistence.dao.IActivityModuleDao;
import com.behappy.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.*;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final IActivityModuleDao activityDao;

    @Autowired
    public ActivityServiceImpl(IActivityModuleDao activityDao) {
        this.activityDao = activityDao;
    }


    @Override
    public List<Activity> getListForDay(Therapy therapy, LocalDate date){
        List<Activity> result = new ArrayList<>();
        for (Activity t : getActivityList(therapy.getId())) {
            if (t.getDate().isEqual(date))
                result.add(t);
        }
        return result;
    }

    @Override
    public List<List<Activity>> getListForWeek(Therapy therapy, LocalDate startDay){
        List<List<Activity>> result = new ArrayList<>();
        LocalDate end = startDay.plusDays(7);

        while (startDay.isBefore(end)){
            result.add(getListForDay(therapy,startDay));
            startDay = startDay.plusDays(1);
        }
        return result;
    }

    @Override
    public long getSumOfMarksByDay(Therapy therapy, int year, int month, int day) {
        List<Activity> list = getListForDay(therapy,LocalDate.of(year,month,day));

        long sum =0;
        for (Activity us : list){
            sum+= us.getMark();
        }
        return sum;
    }

    private List<LocalDate> getListOfDaysInRange(LocalDate begin, LocalDate end){
        LocalDate d = begin;
        List<LocalDate> list = new ArrayList<>();
        while ( d.isBefore(end.plusDays(1) ) ){
            list.add(d);
            d=d.plusDays(1);
        }
        return list;
    }

    @Override
    public long getActivityTimeInMinutesByDay(Therapy therapy, int year, int month, int day) {
        List<Activity> list = getListForDay(therapy,LocalDate.of(year,month,day));
        long sum =0;
        for (Activity us : list){
            sum+= us.getPeriodInMinutes();
        }
        return sum;
    }

    @Override
    public double getAVGofMarksByMonth(Therapy therapy, int year, int month) {
        LocalDate initial = LocalDate.of(year,month,1);
        return getAVGofMarksByRange(therapy,initial.with(firstDayOfMonth()),initial.with(lastDayOfMonth()));
    }

    @Override
    public double getAVGofMarksByYear(Therapy therapy, int year) {
        LocalDate initial = LocalDate.of(year,1,1);
        return getAVGofMarksByRange(therapy,initial.with(firstDayOfYear()),initial.with(lastDayOfYear()));
    }

    @Override
    public double getAVGofMarksByRange(Therapy therapy, LocalDate begin, LocalDate end) {
        List<LocalDate> days = getListOfDaysInRange(begin,end);

        long sum = 0, n=0;

        for(LocalDate day : days){
            sum+= getSumOfMarksByDay(therapy,day.getYear(),day.getMonthValue(),day.getDayOfMonth());
            n++;
        }
        return (double)sum/(double)n;
    }

    @Override
    public double getAVGofTimeActivityRange(Therapy therapy, LocalDate begin, LocalDate end) {
        List<LocalDate> days = getListOfDaysInRange(begin, end);

        long sum = 0, n=0;

        for(LocalDate day : days){
            sum+= getActivityTimeInMinutesByDay(therapy,day.getYear(),day.getMonthValue(),day.getDayOfMonth());
            n++;
        }
        return (double)sum/(double)n;
    }

    @Override
    public double getAVGofTimeActivityByMonth(Therapy therapy, int year, int month) {
        LocalDate initial = LocalDate.of(year,month,1);
        return getAVGofTimeActivityRange(therapy,initial.with(firstDayOfMonth()),initial.with(lastDayOfMonth()));
    }

    @Override
    public double getAVGofTimeActivityByYear(Therapy therapy, int year) {
        LocalDate initial = LocalDate.of(year,1,1);
        return getAVGofTimeActivityRange(therapy,initial.with(firstDayOfYear()),initial.with(lastDayOfYear()));
    }

    private List<Activity> getActivityList(long therapyId){
        return activityDao.find(therapyId).getActivityList();
    }
}
