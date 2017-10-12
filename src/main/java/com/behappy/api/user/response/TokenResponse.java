package com.behappy.api.user.response;

public final class TokenResponse {

    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}