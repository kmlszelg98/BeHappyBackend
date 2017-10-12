package com.behappy.api.therapy.reader;

import com.behappy.api.therapy.response.PermissionsContainer;
import com.behappy.api.therapy.response.TherapyMemberResponse;
import com.behappy.api.therapy.response.TherapyResponse;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.user.User;
import com.behappy.service.DiscoverService;
import com.behappy.service.TherapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(method = RequestMethod.GET)
public class TherapyReaderController {

    private final TherapyService therapyService;
    private final DiscoverService discoveryService;

    @Autowired
    public TherapyReaderController(TherapyService therapyService, DiscoverService discoveryService) {
        this.therapyService = therapyService;
        this.discoveryService = discoveryService;
    }

    @RequestMapping("/api/therapies")
    @ResponseStatus(HttpStatus.OK)
    public List<TherapyResponse> getAllTherapies() {
        User loggedInUser = discoveryService.getLoggedUser();
        return therapyService.getUserTherapies(loggedInUser)
                .stream()
                .map(TherapyResponse::fromTherapy)
                .collect(Collectors.toList());
    }


    @RequestMapping(value = "/api/therapies/{therapy_id}/members")
    @ResponseStatus(HttpStatus.OK)
    public List<TherapyMemberResponse> getAllUsers(@PathVariable long therapy_id) {
        User loggedUser = discoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        checkIsUserInTherapy(loggedUser, therapy);
        return getMemberResponsesFromTherapy(therapy);
    }

    @RequestMapping(value = "/api/therapies/{therapyId}/role")
    @ResponseStatus(HttpStatus.OK)
    public PermissionsContainer getRoleOfLoggedUser(@PathVariable long therapyId){
        User loggedUser = discoveryService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapyId);
        checkIsUserInTherapy(loggedUser, therapy);
        return getPermissionsContainer(loggedUser, therapy);
    }

    private PermissionsContainer getPermissionsContainer(User loggedUser, Therapy therapy){
        String email = loggedUser.getEmail();
        return new PermissionsContainer.Builder()
                .role(discoveryService.discoverRole(loggedUser, therapy).toString())
                .creator(therapy.isCreator(email))
                .special(therapy.isSpecial(email))
                .createContainer();
    }

    private void checkIsUserInTherapy(User user, Therapy therapy){
        if(!therapy.isUserActiveInTherapy(user)){
            throw new SecurityException("You can't perform this action");
        }
    }

    private List<TherapyMemberResponse> getMemberResponsesFromTherapy(Therapy therapy) {
        List<TherapyMemberResponse> memberResponseList = new ArrayList<>();
        memberResponseList.add(TherapyMemberResponse.fromPatient(therapy.getPatient()));
        memberResponseList.addAll(
                therapy.getWardenSet().stream().map(TherapyMemberResponse::fromWarden).collect(Collectors.toList())
        );
        memberResponseList.addAll(
                therapy.getTherapistSet().stream().map(TherapyMemberResponse::fromTherapist).collect(Collectors.toList())
        );

        return memberResponseList;
    }
}
