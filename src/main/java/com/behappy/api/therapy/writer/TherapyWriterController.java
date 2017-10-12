package com.behappy.api.therapy.writer;

import com.behappy.api.therapy.request.EmailRequest;
import com.behappy.api.therapy.request.TherapyRequest;
import com.behappy.api.therapy.request.UserAddRequest;
import com.behappy.api.therapy.response.TherapyResponse;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.therapy.TherapyInvitation;
import com.behappy.domain.model.user.RoleEnum;
import com.behappy.domain.model.user.User;
import com.behappy.domain.settings.Settings;
import com.behappy.exceptions.RoleNotAvailableException;
import com.behappy.exceptions.TherapyAlreadyExistsException;
import com.behappy.exceptions.UserAlreadyInTherapyException;
import com.behappy.mail.MailSender;
import com.behappy.service.DiscoverService;
import com.behappy.service.InvitationService;
import com.behappy.service.TherapyService;
import com.behappy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
public class TherapyWriterController {


    private TherapyService therapyService;
    private UserService userService;
    private InvitationService invitationService;
    private MailSender mailSender;
    private DiscoverService discoverService;
    private final AddUtil addUtil;

    @Autowired
    public TherapyWriterController(TherapyService therapyService,
                                   UserService userService,
                                   InvitationService invitationService,
                                   MailSender mailSender,
                                   DiscoverService discoveryService, AddUtil addUtil){
        this.therapyService = therapyService;
        this.userService = userService;
        this.invitationService = invitationService;
        this.mailSender = mailSender;
        this.discoverService = discoveryService;
        this.addUtil = addUtil;
    }

    @RequestMapping(value = "/api/therapies", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TherapyResponse create(@Valid @RequestBody TherapyRequest request) {
        User loggedUser = discoverService.getLoggedUser();
        if(therapyService.therapyExists(request.getName() , loggedUser)) {
            throw new TherapyAlreadyExistsException("Therapy with given name already exists for the user");
        }
        checkAccountOccurrence(loggedUser, request.getRole());
        Therapy newTherapy = therapyService.create(request, loggedUser);
        return TherapyResponse.fromTherapy(newTherapy);
    }

    @RequestMapping(value = "/api/therapies/{therapy_id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public TherapyResponse edit(@RequestBody TherapyRequest therapyRequest, @PathVariable long therapy_id) {
        User loggedUser = discoverService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        checkIsUserInTherapy(loggedUser, therapy);

        if (!isPatientOrCreator(loggedUser, therapy)) {
            throw new SecurityException("Issuer has no authority to perform this operation");
        }
        Therapy updatedTherapy = therapyService.editTherapy(therapy, therapyRequest);
        return TherapyResponse.fromTherapy(updatedTherapy);
    }

    @RequestMapping(value = "/api/therapies/{therapy_id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long therapy_id) {
        Therapy therapy = therapyService.getTherapy(therapy_id);
        User loggedUser = discoverService.getLoggedUser();
        checkIsUserInTherapy(loggedUser, therapy);

        if(!isPatientOrCreator(loggedUser, therapy)){
            throw new SecurityException("Issuer unauthorized to perform this operation.");
        }
        therapyService.removeTherapy(therapy);
    }

    @RequestMapping(value = "/api/therapies/{therapy_id}/members", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addNewTherapyMember(@Valid @RequestBody UserAddRequest userAddRequest,
                    @PathVariable long therapy_id) throws MessagingException {
        User loggedUser = discoverService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);
        checkAddSecurity(loggedUser, therapy, userAddRequest.getRole());
        TherapyInvitation therapyInvitation = new TherapyInvitation(therapy.getId(), userAddRequest.getRole(), userAddRequest.getEmail(), loggedUser.getEmail());
        invitationService.add(therapyInvitation);
        String link = generateLink(therapyInvitation.getId(), userAddRequest, therapy);
        mailSender.send(userAddRequest.getEmail(), link);
    }

    @RequestMapping(value = "/api/therapies/{therapyId}/members", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removeUserFromTherapy(@RequestBody EmailRequest email, @PathVariable long therapyId){
        User loggedUser = discoverService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapyId);

        if(!isPatientOrCreator(loggedUser, therapy)){
            throw new SecurityException("Issuer has no authority to perform this operation");
        }

        User removeUser = userService.getUser(email.getEmail());
        checkRemovalPrivileges(loggedUser, removeUser, therapy);
        therapyService.removeUserFromTherapy(removeUser, therapy);
    }

    @RequestMapping(value = "/api/therapies/{therapyId}/assignSpecial", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void assignSpecialWarden(@RequestBody EmailRequest email, @PathVariable long therapyId){
        User loggedUser = discoverService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapyId);
        checkIsUserInTherapy(loggedUser, therapy);

        if(therapy.discoverRole(loggedUser) != RoleEnum.PATIENT){
            throw new SecurityException("Issuer has no authority to perform this operation");
        }

        User special = userService.getUser(email.getEmail());
        if(!therapy.isUserActiveInTherapy(special) || therapy.discoverRole(special) != RoleEnum.WARDEN){
            throw new RoleNotAvailableException("Role unavailable");
        }

        therapyService.setWardenToSpecial(therapy, special);
    }

    // HELPER METHODS

    private void checkAddSecurity(User loggedUser, Therapy therapy, RoleEnum role){
        checkIsUserInTherapy(loggedUser, therapy);

        if(!therapy.isRoleFree(role)) {
            throw new RoleNotAvailableException("Role unavailable");
        }

        if(!isPatientOrCreator(loggedUser, therapy)){
            throw new SecurityException("Issuer has no authority to perform this operation");
        }
    }

    private String generateLink(String invitationId, UserAddRequest addUser, Therapy therapy){
        String email = addUser.getEmail();
        if(userService.exists(email)) {
            User user = userService.getUser(email);
            if(therapy.isUserInTherapy(user)) {
                throw new UserAlreadyInTherapyException("User already in therapy");
            }
            checkAccountOccurrence(user, addUser.getRole());
            therapyService.assignUser(user, therapy, addUser.getRole());
            return Settings.URL + "/invitation/" + invitationId;
        } else {
            return Settings.URL + "/register/invite/" + invitationId;
        }
    }

    private boolean isPatientOrCreator(User loggedUser, Therapy therapy){
        checkIsUserInTherapy(loggedUser, therapy);
        return discoverService.discoverRole(loggedUser, therapy) == RoleEnum.PATIENT
                || therapy.getCreator().equals(loggedUser.getEmail());
    }

    private void checkIsUserInTherapy(User user, Therapy therapy){
        if(!therapy.isUserInTherapy(user)){
            throw new SecurityException(("Cannot access therapy with given id"));
        }
    }

    private void checkAccountOccurrence(User user, RoleEnum role){
         if(addUtil.isCorrectToAdd(user, role)){
             throw new RoleNotAvailableException("Role is not avaible");
         }
    }

    private void checkRemovalPrivileges(User loggedUser, User toRemove, Therapy therapy) {
        String loggedEmail = loggedUser.getEmail();
        String removeEmail = toRemove.getEmail();
        if(therapy.getCreator().equals(loggedEmail) && !therapy.getInviterMailOf(removeEmail).equals(loggedEmail) ) {
            throw new SecurityException("Issuer has no authority to perform this operation");
        }
    }
}
