package com.behappy.api.activity.reader;

import com.behappy.api.activity.response.DailyActivitiesResponse;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.user.User;
import com.behappy.service.ActivityService;
import com.behappy.service.DiscoverService;
import com.behappy.service.TherapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/activity/", method = RequestMethod.GET)
public class ActivityControllerReader {

    private final ActivityService activityService;
    private final TherapyService therapyService;
    private final DiscoverService roleDiscoveryService;

    @Autowired
    public ActivityControllerReader(ActivityService activityService,
                                    TherapyService therapyService, DiscoverService roleDiscoveryService) {
        this.activityService = activityService;
        this.therapyService = therapyService;
        this.roleDiscoveryService = roleDiscoveryService;
    }


    // +
    @RequestMapping(value = "{therapy_id}/timeAvg/range")
    @ResponseStatus(HttpStatus.OK)
    public Double getActiveTimeAVGByRange(@PathVariable long therapy_id,
                                                        @RequestParam(value="begindate")
                                                        @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate beginDate,
                                                        @RequestParam(value="enddate")
                                                        @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate ) {

        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        isUserInTherapyCheck(therapy, loggedUser);
        checkDateRangeOK(beginDate, endDate);

        return activityService.getAVGofTimeActivityRange(therapy,beginDate,endDate);
    }

    // +
    @RequestMapping(value = "{therapy_id}/time/{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.OK)
    public Long getActivityByDay(@PathVariable("therapy_id") long therapy_id, @PathVariable("year") int year,
                                                   @PathVariable("month") int month,@PathVariable("day") int day) {

        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        isUserInTherapyCheck(therapy, loggedUser);
        return activityService.getActivityTimeInMinutesByDay(therapy,year,month,day);
    }

    // ?
    @RequestMapping(value = "{therapy_id}/timeAVG/{year}/{month}")
    @ResponseStatus(HttpStatus.OK)
    public Double getActivityAVGByMonth(@PathVariable("therapy_id") long therapy_id, @PathVariable("year") int year,
                                                   @PathVariable("month") int month) {

        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        isUserInTherapyCheck(therapy, loggedUser);
        return activityService.getAVGofTimeActivityByMonth(therapy,year,month);
    }

    // ?
    @RequestMapping(value = "{therapy_id}/timeAVG/{year}")
    @ResponseStatus(HttpStatus.OK)
    public Double getActivityAVGByYear(@PathVariable("therapy_id") long therapy_id, @PathVariable("year") int year){

        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        isUserInTherapyCheck(therapy, loggedUser);
        return activityService.getAVGofTimeActivityByYear(therapy,year);
    }

    // ?
    @RequestMapping(value = "{therapy_id}/effortAvg/range")
    @ResponseStatus(HttpStatus.OK)
    public Double getByEffortAVGByRange(@PathVariable long therapy_id,
                                                        @RequestParam(value="begindate")
                                                        @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate beginDate,
                                                        @RequestParam(value="enddate")
                                                        @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate ) {

        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        isUserInTherapyCheck(therapy, loggedUser);
        return activityService.getAVGofMarksByRange(therapy,beginDate,endDate);
    }

    // ?
    @RequestMapping(value = "{therapy_id}/effort/{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.OK)
    public Long getEffortByDay(@PathVariable("therapy_id") long therapy_id, @PathVariable("year") int year,
                                                 @PathVariable("month") int month,@PathVariable("day") int day) {

        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        isUserInTherapyCheck(therapy, loggedUser);
        return activityService.getSumOfMarksByDay(therapy,year,month,day);
    }

    // ?
    @RequestMapping(value = "{therapy_id}/effortAVG/{year}/{month}")
    @ResponseStatus(HttpStatus.OK)
    public Double getEffortAVGByMonth(@PathVariable("therapy_id") long therapy_id, @PathVariable("year") int year,
                                                        @PathVariable("month") int month) {

        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        isUserInTherapyCheck(therapy, loggedUser);
        return activityService.getAVGofMarksByMonth(therapy,year,month);
    }

    // -
    @RequestMapping(value = "{therapy_id}/effortAVG/{year}")
    @ResponseStatus(HttpStatus.OK)
    public Double getEffortAVGByYear(@PathVariable("therapy_id") long therapy_id, @PathVariable("year") int year){

        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        isUserInTherapyCheck(therapy, loggedUser);
        return activityService.getAVGofMarksByYear(therapy,year);
    }

    // +
    @RequestMapping(value = "{therapy_id}/byDay")
    @ResponseStatus(HttpStatus.OK)
    public DailyActivitiesResponse getActivitiesByDay(@PathVariable long therapy_id,
                                                    @RequestParam(value="beginDate")
                                                    @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate day){

        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        isUserInTherapyCheck(therapy, loggedUser);
        return new DailyActivitiesResponse(day, activityService.getListForDay(therapy,day));

    }

    // -
    @RequestMapping(value = "{therapy_id}/byWeek")
    @ResponseStatus(HttpStatus.OK)
    public List<DailyActivitiesResponse> getActivitiesByWeek(@PathVariable long therapy_id,
                                                            @RequestParam(value="beginDate")
                                                            @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDay){

        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        isUserInTherapyCheck(therapy, loggedUser);
        return activityService.getListForWeek(therapy,startDay).stream().
                map(e -> new DailyActivitiesResponse(e.get(0).getDate(),e)).collect(Collectors.toList());
    }

    private void isUserInTherapyCheck(Therapy therapy, User user){
        if(!therapy.isUserActiveInTherapy(user)) {
            throw new SecurityException("Cannot access therapy with given id");
        }
    }

    private void checkDateRangeOK(LocalDate beginDate, LocalDate endDate) {
        if(endDate.isBefore(beginDate)) {
            throw new IllegalArgumentException("End date before beginning date in request.");
        }
    }

}