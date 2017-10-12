package com.behappy.service.impl;

import com.behappy.api.mood.response.MoodResponse;
import com.behappy.domain.model.module.Module;
import com.behappy.domain.model.module.impl.MoodModule;
import com.behappy.domain.model.therapy.Mood;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.exceptions.ModuleDisabledException;
import com.behappy.persistence.dao.impl.MoodModuleDao;
import com.behappy.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class MoodServiceImpl implements MoodService {

    private final MoodModuleDao moduleDao;

    @Autowired
    public MoodServiceImpl(MoodModuleDao moduleDao) {
        this.moduleDao = moduleDao;
    }

    @Override
    public void addTherapyStatistic(Mood mood, long therapyId) {
        MoodModule moodModule = moduleDao.find(therapyId);
        checkModuleActivity(moodModule);
        for (Mood m : moodModule.getMoodList()) {
            if (m.getDate().equals(mood.getDate())) {
                m.setMark(mood.getMark());
                m.setFear(mood.getFear());
                m.setAutomatic(mood.getAutomatic());
                moduleDao.save(moodModule);
                return;
            }
        }
        moduleDao.save(moodModule);
    }

    @Override
    public MoodResponse generates(Therapy therapy, LocalDate begin, LocalDate end) {

        int bestVal = 14;

        if (begin.isEqual(end) || begin.isAfter(end))
            throw new IllegalArgumentException("Begin of the period after end");

        long daysAmount = ChronoUnit.DAYS.between(begin, end);
        long weeksAmount = ChronoUnit.WEEKS.between(begin, end);
        long monthsAmount = ChronoUnit.MONTHS.between(begin, end);
        long yearsAmount = ChronoUnit.YEARS.between(begin, end);

        MoodResponse.Option option = MoodResponse.Option.Days;
        if (Math.abs(daysAmount - bestVal) > Math.abs(weeksAmount - bestVal)) {
            option = MoodResponse.Option.Weeks;
            if (Math.abs(weeksAmount - bestVal) > Math.abs(monthsAmount - bestVal)) {
                option = MoodResponse.Option.Month;
                if (Math.abs(monthsAmount - bestVal) > Math.abs(yearsAmount - bestVal)) {
                    option = MoodResponse.Option.Years;
                }
            }
        }
        List<Double> moods = null;
        List<Double> fears = null;

        switch (option) {
            case Days:
                moods = getAverageMoodsByDay(therapy, begin, end);
                fears = getAverageFearsByDay(therapy, begin, end);
                break;
            case Weeks:
                moods = getAverageMoodsByWeek(therapy, begin, end);
                fears = getAverageFearsByWeek(therapy, begin, end);
                break;
            case Month:
                moods = getAverageMoodsByMonth(therapy, begin, end);
                fears = getAverageFearsByMonth(therapy, begin, end);
                break;
            case Years:
                moods = getAverageMoodsByYear(therapy, begin, end);
                fears = getAverageFearsByYear(therapy, begin, end);
                break;
        }

        return new MoodResponse(moods, fears, option);

    }

    @Override
    public MoodModule findMoodModule(long therapyId) {
        return moduleDao.find(therapyId);
    }

    private List<Mood> getByTherapyAndDateRange(Therapy therapy, LocalDate beginDate,
                                                LocalDate endDate) {
        MoodModule moodModule = findMoodModule(therapy.getId());
        List<Mood> result = new ArrayList<>();
        for (Mood tm : moodModule.getMoodList()) {
            if (tm.getDate().isAfter(beginDate) || tm.getDate().isEqual(beginDate)) {
                if (tm.getDate().isBefore(endDate) || tm.getDate().isEqual(endDate)) {
                    result.add(tm);
                }
            }
        }
        return result;
    }

    //General generators
    private double moodAvgOnPeriod(Therapy therapy, LocalDate begin, LocalDate end) {
        List<Mood> list = getByTherapyAndDateRange(therapy, begin, end);
        Integer i = list.stream().map(Mood::getMark).reduce(0, Integer::sum);
        Double d = (double) i / (double) list.size();
        return list.size() == 0 ? (double) 0 : d;

    }

    private double fearAvgOnPeriod(Therapy therapy, LocalDate begin, LocalDate end) {
        List<Mood> list = getByTherapyAndDateRange(therapy, begin, end);
        Integer i = list.stream().map(Mood::getFear).reduce(0, Integer::sum);
        Double d = (double) i / (double) list.size();
        return list.size() == 0 ? (double) 0 : d;
    }

    //Day
    private List<Double> getAverageMoodsByDay(Therapy therapy, LocalDate begin, LocalDate end) {
        List<Double> list = new LinkedList<>();
        while (!begin.isAfter(end)) {
            list.add(moodAvgOnPeriod(therapy, begin, begin));
            begin = begin.plusDays(1);
        }
        return list;
    }

    private List<Double> getAverageFearsByDay(Therapy therapy, LocalDate begin, LocalDate end) {
        List<Double> list = new LinkedList<>();
        while (!begin.isAfter(end)) {
            list.add(fearAvgOnPeriod(therapy, begin, begin));
            begin = begin.plusDays(1);
        }
        return list;
    }

    //Week
    private List<Double> getAverageMoodsByWeek(Therapy therapy, LocalDate begin, LocalDate end) {
        List<Double> list = new LinkedList<>();
        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek(); //Nie ma Polski, a we Francji też liczą tydzień od poniedziałku
        LocalDate iter = begin.with(fieldISO, 1);//daje pierwszy dzien danego tygodnia
        while (!iter.isAfter(end)) {
            list.add(moodAvgOnPeriod(therapy, iter, iter.plusDays(6)));
            iter = iter.plusDays(7);
        }
        return list;
    }

    private List<Double> getAverageFearsByWeek(Therapy therapy, LocalDate begin, LocalDate end) {
        List<Double> list = new LinkedList<>();
        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek(); //Nie ma Polski, a we Francji też liczą tydzień od poniedziałku
        LocalDate iter = begin.with(fieldISO, 1);//daje pierwszy dzien danego tygodnia
        while (!iter.isAfter(end)) {
            list.add(fearAvgOnPeriod(therapy, iter, iter.plusDays(6)));
            iter = iter.plusDays(7);
        }
        return list;
    }

    //Month
    private List<Double> getAverageMoodsByMonth(Therapy therapy, LocalDate begin, LocalDate end) {
        List<Double> list = new LinkedList<>();
        LocalDate periodBeg = begin.withDayOfMonth(1);
        while (!periodBeg.isAfter(end)) {
            LocalDate periodEnd = periodBeg.withDayOfMonth(periodBeg.lengthOfMonth());
            list.add(moodAvgOnPeriod(therapy, periodBeg, periodEnd));
            periodBeg = periodEnd.plusDays(1);
        }
        return list;
    }

    private List<Double> getAverageFearsByMonth(Therapy therapy, LocalDate begin, LocalDate end) {
        List<Double> list = new LinkedList<>();
        LocalDate periodBeg = begin.withDayOfMonth(1);
        while (!periodBeg.isAfter(end)) {
            LocalDate periodEnd = periodBeg.withDayOfMonth(periodBeg.lengthOfMonth());
            list.add(fearAvgOnPeriod(therapy, periodBeg, periodEnd));
            periodBeg = periodEnd.plusDays(1);
        }
        return list;
    }

    //Year
    private List<Double> getAverageMoodsByYear(Therapy therapy, LocalDate begin, LocalDate end) {
        List<Double> list = new LinkedList<>();
        LocalDate periodBeg = begin.withDayOfYear(1);
        while (!periodBeg.isAfter(end)) {
            LocalDate periodEnd = periodBeg.withDayOfYear(periodBeg.lengthOfYear());
            list.add(moodAvgOnPeriod(therapy, periodBeg, periodEnd));
            periodBeg = periodEnd.plusDays(1);
        }
        return list;
    }

    private List<Double> getAverageFearsByYear(Therapy therapy, LocalDate begin, LocalDate end) {
        List<Double> list = new LinkedList<>();
        LocalDate periodBeg = begin.withDayOfYear(1);
        while (!periodBeg.isAfter(end)) {
            LocalDate periodEnd = periodBeg.withDayOfYear(periodBeg.lengthOfYear());
            list.add(fearAvgOnPeriod(therapy, periodBeg, periodEnd));
            periodBeg = periodEnd.plusDays(1);
        }
        return list;
    }

    private void checkModuleActivity(Module module){
        if (!module.isActive()) {
            throw new ModuleDisabledException("This module is disabled for this therapy");
        }
    }
}