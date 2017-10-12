package com.behappy.api.activity.writer;

import com.behappy.api.activity.request.ActivityContainerRequest;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.therapy.Activity;
import com.behappy.domain.model.user.User;
import com.behappy.service.ActivityAddService;
import com.behappy.service.DiscoverService;
import com.behappy.service.TherapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ActivityControllerWriter {

    private final ActivityAddService activityService;
    private final TherapyService therapyService;
    private final DiscoverService discoveryService;

    @Autowired
    public ActivityControllerWriter(ActivityAddService activityService,
                                    TherapyService therapyService, DiscoverService roleDiscoveryService) {
        this.activityService = activityService;
        this.therapyService = therapyService;
        this.discoveryService = roleDiscoveryService;
    }

    @PostMapping(value = "/api/activity/{therapyId}/add")
    public void addUserActivity(@RequestBody ActivityContainerRequest containerRequest,  @PathVariable long therapyId)
            throws AuthenticationException {
        User user = discoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapyId);
        checkPrivileges(user, therapy);
        checkDate(containerRequest.getDate());

        List<Activity> activities = containerRequest.getActivities();
        activityService.addTherapyActivity(therapy.getId(), activities);
    }

    private void checkDate(LocalDate date) {
        if(date.isBefore(LocalDate.now().minusDays(6))){
            throw new IllegalArgumentException("Cannot add statistic more than 6 days in the past");
        }
    }

    private void checkPrivileges(User user, Therapy therapy) throws AuthenticationException {
        if(!therapy.isUserActiveInTherapy(user) || !isSpecialOrPatient(user, therapy)) {
            throw new AuthenticationException("Issuer has no authority to perform this operation");
        }
    }

    private boolean isSpecialOrPatient(User user, Therapy therapy){
        return therapy.isSpecial(user.getEmail())
                || therapy.getPatient().getMail().equals(user.getEmail());
    }
}
