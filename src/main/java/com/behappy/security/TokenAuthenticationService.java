package com.behappy.security;

import com.behappy.domain.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.codec.Base64;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Bartosz on 25.11.2016.
 */
class TokenAuthenticationService {

    private final String HEADER_NAME = "X-AUTH-TOKEN";
    private final String secret;

    TokenAuthenticationService(String secret) {
        this.secret = secret;
    }

    UserAuthentication getAuthentication(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(HEADER_NAME);
        if(token != null) {
            User user = parseUserFromToken(token);
            if(user != null)
                return new UserAuthentication(user);
        }
        return null;
    }

    private User parseUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.encode(secret.getBytes()))
                .parseClaimsJws(token)
                .getBody();

        String email = claims.get("email", String.class);
        return new User(email);
    }

    static final String TYP_KEY = "typ";
    static final String JWT = "JWT";
}
