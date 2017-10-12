package com.behappy.security;

import com.behappy.domain.model.user.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.codec.Base64;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartosz on 26.11.2016.
 */

public class TokenGenerator {

    private TokenGenerator(){}

    public static String generateToken(User user) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("email",user.getEmail());
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setHeaderParam(TokenAuthenticationService.TYP_KEY,TokenAuthenticationService.JWT)
                .signWith(SignatureAlgorithm.HS256, Base64.encode(SpringSecurityConfig.SECRET.getBytes()));
        return jwtBuilder.compact();
    }
}
