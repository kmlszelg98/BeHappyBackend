package com.behappy.api.user.reader;

import com.behappy.api.user.request.LoginPassword;
import com.behappy.api.user.response.TokenResponse;
import com.behappy.api.user.response.UserResponse;
import com.behappy.domain.model.user.User;
import com.behappy.security.TokenGenerator;
import com.behappy.service.DiscoverService;
import com.behappy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserReaderController {

    private final UserService userService;
    private final DiscoverService discoverService;

    @Autowired
    public UserReaderController(UserService userService, DiscoverService discoverService) {
        this.userService = userService;
        this.discoverService = discoverService;
    }

    @RequestMapping(value = "/api/users/login", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestHeader(value = "Authorization") String credentials) throws AuthenticationException {
        LoginPassword loginPassword = UserService.decodeUserAndPassword(credentials);
        User user = userService.getUser(loginPassword.getLogin());

        if (!user.getPassword().equals(loginPassword.getPassword())) {
            throw new AuthenticationException("Authentication failed");
        }

        String token = TokenGenerator.generateToken(user);
        return new TokenResponse(token);
    }

    @RequestMapping(value = "/api/users/current", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getCurrentUser() {
        return new UserResponse(discoverService.getLoggedUser());
    }

    @RequestMapping(value = "/api/users/confirm/1")
    @ResponseStatus(HttpStatus.OK)
    public String test() {
        return "dupa";
    }
}
