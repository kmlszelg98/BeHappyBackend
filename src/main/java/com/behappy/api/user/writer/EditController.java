package com.behappy.api.user.writer;

import com.behappy.api.user.request.EditRequest;
import com.behappy.api.user.request.ForgottenPasswordRequest;
import com.behappy.api.user.response.TokenResponse;
import com.behappy.domain.model.EmailContainer;
import com.behappy.domain.model.user.User;
import com.behappy.security.TokenGenerator;
import com.behappy.service.DiscoverService;
import com.behappy.service.EmailContainerService;
import com.behappy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "api/users/", method = RequestMethod.PATCH)
public class EditController {

    private final UserService userService;
    private final DiscoverService discoveryService;
    private final EmailContainerService emailContainerService;

    @Autowired
    public EditController(UserService userService, DiscoverService discoveryService, EmailContainerService emailContainerService) {
        this.userService = userService;
        this.discoveryService = discoveryService;
        this.emailContainerService = emailContainerService;
    }

    @RequestMapping(value = "edit")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse editUser(@Valid @RequestBody EditRequest editRequest) throws IllegalAccessException {
        User currentUser = discoveryService.getLoggedUser();
        if (!currentUser.getPassword().equals(editRequest.getCurrentPassword())) {
            throw new SecurityException("Issuer has no authority to perform the operation.");
        }
        User editedUser = userService.editUser(currentUser.getEmail(), editRequest.createUser());
        String token = TokenGenerator.generateToken(editedUser);
        return new TokenResponse(token);
    }

    @RequestMapping(value= "edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@Valid @RequestBody ForgottenPasswordRequest forgottenPasswordRequest, @PathVariable String id) throws IllegalAccessException{
        EmailContainer emailContainer = emailContainerService.find(id)
                .orElseThrow(() -> new IllegalArgumentException("invalid link, try again"));

        if(!emailContainer.getEmail().equals(forgottenPasswordRequest.getEmail())){
            throw new SecurityException("Email user provided in request does not match his account email.");
        }
        if(isPasswordSameAsOldOne(forgottenPasswordRequest.getEmail(), forgottenPasswordRequest.getNewPassword())){
            throw new IllegalArgumentException("Your new password can not be the same as previous one!");
        }
        emailContainerService.delete(emailContainer.getId());
        userService.editUser(forgottenPasswordRequest.getEmail(),forgottenPasswordRequest.createUser());
    }

    private boolean isPasswordSameAsOldOne(String email, String newPassword){
        return userService.getUser(email).getPassword().equals(newPassword);
    }
}
