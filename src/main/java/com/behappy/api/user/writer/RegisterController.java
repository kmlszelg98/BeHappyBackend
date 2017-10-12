package com.behappy.api.user.writer;

import com.behappy.api.user.request.PasswordReminderRequest;
import com.behappy.api.user.request.PasswordRequest;
import com.behappy.api.user.request.UserRequest;
import com.behappy.api.user.response.TokenResponse;
import com.behappy.domain.model.*;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.therapy.TherapyInvitation;
import com.behappy.domain.model.user.RoleEnum;
import com.behappy.domain.model.user.User;
import com.behappy.domain.settings.Settings;
import com.behappy.exceptions.NoSuchUserException;
import com.behappy.mail.MailSender;
import com.behappy.security.TokenGenerator;
import com.behappy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
public class RegisterController {

    private UserService userService;
    private MailSender mailSender;
    private EnabledUserService enabledUserService;
    private TherapyService therapyService;
    private InvitationService invitationService;
    private DiscoverService discoverService;
    private EmailContainerService emailContainerService;

    @Autowired
    public RegisterController(MailSender mailSender,
                              EnabledUserService enabledUserService,
                              UserService userService,
                              TherapyService therapyService,
                              InvitationService invitationService,
                              DiscoverService discoverService,
                              EmailContainerService emailContainerService) {

        this.mailSender = mailSender;
        this.enabledUserService = enabledUserService;
        this.userService = userService;
        this.therapyService = therapyService;
        this.invitationService = invitationService;
        this.discoverService = discoverService;
        this.emailContainerService = emailContainerService;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse register(@Valid @RequestBody UserRequest userRequest) throws MessagingException {
        if(!userService.exists(userRequest.getEmail())) {
            User user = new User(userRequest.getEmail(), userRequest.getPassword(), null, userRequest.getName());
            userService.addUser(user);

            //TODO: Better welcome message
            String welcomeMessage = "Witamy w BeHappy, " + userRequest.getName() + "!\n";
            mailSender.send(userRequest.getEmail(), welcomeMessage);

            String token = TokenGenerator.generateToken(user);
            return new TokenResponse(token);
        }
        else throw new IllegalArgumentException("User with given mail already registered.");
    }

    //INVITE GDY SIE REJESTRUJE
    @RequestMapping(value = "/api/users/invite/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse registerFromInvitation(@Valid @RequestBody PasswordRequest passwordRequest,
                                                @PathVariable String id) {
        //TODO: Refactor
        TherapyInvitation therapyInvitation = invitationService.find(id);
        invitationService.remove(id);
        Therapy therapy = therapyService.getTherapy(therapyInvitation.getTherapyId());
        User user = new User(therapyInvitation.getEmail(), passwordRequest.getPassword());
        userService.addUser(user);
        assignUserAndActivate(therapy, user, therapyInvitation.getRoleEnum(), therapyInvitation.getInviterMail());
        String token = TokenGenerator.generateToken(user);
        return new TokenResponse(token);
    }

    private void assignUserAndActivate(Therapy therapy, User user, RoleEnum role, String inviterMail){
        therapy.addUser(user, role);
        therapy.activateUser(user.getEmail(), inviterMail);
        therapyService.save(therapy);
    }

    //INVITATION GDY USER ISTNIEJE
    @RequestMapping(value = "/api/users/invitation/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void approveTherapyInvitation(@PathVariable String id) {
        TherapyInvitation therapyInvitation = invitationService.find(id);
        invitationService.remove(id);
        Therapy therapy = therapyService.getTherapy(therapyInvitation.getTherapyId());
        if (discoverService.getLoggedUser().getEmail().equals(therapyInvitation.getEmail())) {
            therapyService.activateUser(discoverService.getLoggedUser(), therapyInvitation.getInviterMail(), therapy);
        } else {
            throw new SecurityException("Email from invitation does not match email of currently logged user!");
        }
    }

    @RequestMapping(value = "/api/users/invitation/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void declineTherapyInvitation(@PathVariable String id) {
        invitationService.remove(id);
    }

    @RequestMapping(value = "/api/users/remind",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void remindPassword(@Valid @RequestBody PasswordReminderRequest passwordReminderRequest) throws MessagingException{
        if(!userService.exists(passwordReminderRequest.getEmail())){
            throw new NoSuchUserException("No user matches given email!");
        }
        EmailContainer emailContainer=new EmailContainer(passwordReminderRequest.getEmail());
        emailContainerService.add(emailContainer);
        String confirmationLink = "localhost:8080/api/users/edit/" + emailContainer.getId();
        mailSender.send(passwordReminderRequest.getEmail(),"Jeśli chcesz zmienić hasło wejdź w link:\n"+ confirmationLink);
    }
}
