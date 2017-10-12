package com.behappy.api.mood.writer;

import com.behappy.api.mood.request.MoodRequest;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.therapy.Mood;
import com.behappy.domain.model.user.RoleEnum;
import com.behappy.domain.model.user.User;
import com.behappy.domain.model.news.StatisticFilledInEvent;
import com.behappy.service.DiscoverService;
import com.behappy.service.NewsService;
import com.behappy.service.TherapyService;
import com.behappy.service.MoodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class MoodControllerWriter {

    private final MoodService moodService;
    private final TherapyService therapyService;
    private final NewsService newsService;
    private final DiscoverService roleDiscoveryService;


    //todo 4 dependencies for adding stats??
    @Autowired
    public MoodControllerWriter(MoodService moodService,
                                TherapyService therapyService,
                                DiscoverService roleDiscoveryService,
                                NewsService newsService) {
        this.moodService = moodService;
        this.therapyService = therapyService;
        this.newsService = newsService;
        this.roleDiscoveryService = roleDiscoveryService;
    }

    @RequestMapping(value = "/api/mood/{therapyId}/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTherapyStatistic(@RequestBody MoodRequest moodRequest,
                                    @PathVariable long therapyId) {
        User loggedUser = roleDiscoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapyId);

        if(canAddStatistic(therapy, loggedUser)){
            throw new SecurityException("You need to be special or patient to add statistic");
        }

        validate(moodRequest);
        Mood mood = new Mood(moodRequest.getDate(), moodRequest.getMark(),
                moodRequest.getFear(), moodRequest.getAutomatic());
        moodService.addTherapyStatistic(mood, therapy.getId());
        newsService.publish(new StatisticFilledInEvent(therapy, loggedUser, mood));
    }

    private boolean canAddStatistic(Therapy therapy, User user){
        if(!therapy.isUserActiveInTherapy(user))
            throw new SecurityException("Cannot access therapy with given id");

        return therapy.isSpecial(user.getEmail()) || therapy.discoverRole(user) == RoleEnum.PATIENT;
    }

    private void validate(MoodRequest mood) {
        if (mood.getMark() > 10 || mood.getMark() <= 0) {
            throw new IllegalArgumentException("Mark must be a number from 1 to 10");
        }
        if (mood.getDate().isBefore(LocalDate.now().minusDays(6))) {
            throw new IllegalArgumentException("Cannot add statistic more than 6 days in the past");
        }
        if (mood.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot add a future statistic");
        }
    }
}