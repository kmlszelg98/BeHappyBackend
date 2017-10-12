package com.behappy.api.mood.reader;

import com.behappy.api.mood.request.LocalDateRequest;
import com.behappy.api.mood.response.MoodResponse;
import com.behappy.domain.model.therapy.Mood;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.user.User;
import com.behappy.service.DiscoverService;
import com.behappy.service.MoodService;
import com.behappy.service.TherapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
public class MoodControllerReader {

    private final MoodService moodService;
    private final TherapyService therapyService;
    private final DiscoverService roleDiscoveryService;

    @Autowired
    public MoodControllerReader(MoodService moodService,
                                TherapyService therapyService,
                                DiscoverService roleDiscoveryService) {
        this.moodService = moodService;
        this.therapyService = therapyService;
        this.roleDiscoveryService = roleDiscoveryService;
    }

    @GetMapping(value = "/api/mood/{therapyId}")
    public ResponseEntity getFormForDate(@RequestBody LocalDateRequest date,
                                         @PathVariable long therapyId){
        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapyId);

        if(!therapy.isUserActiveInTherapy(loggedUser)) {
            throw new SecurityException("Cannot access therapy with given id");
        }

        Optional<Mood> moodOptional = moodService.findMoodModule(therapyId).getMoodByDate(date.getDate());

        if(moodOptional.isPresent()){
            return new ResponseEntity<>(moodOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/api/mood/{therapy_id}/getStats")
    @ResponseStatus(HttpStatus.OK)
    public MoodResponse getStatsByRange(@PathVariable long therapy_id,
                                        @RequestParam(value="begindate")
                                              @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate beginDate,
                                        @RequestParam(value="enddate")
                                              @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate ){

        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);

        if(!therapy.isUserActiveInTherapy(loggedUser)) {
            throw new SecurityException("Cannot access therapy with given id");
        }

        return moodService.generates(therapy,beginDate,endDate);
    }

    @GetMapping(value = "/api/mood/{therapy_id}/getRange")
    @ResponseStatus(HttpStatus.OK)
    public List<LocalDate> getRange(@PathVariable long therapy_id){
        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);

        if(!therapy.isUserActiveInTherapy(loggedUser)) {
            throw new SecurityException("Cannot access therapy with given id");
        }
        return Arrays.asList(therapy.getBeginningDate().toLocalDate(),LocalDate.now());
    }
}

