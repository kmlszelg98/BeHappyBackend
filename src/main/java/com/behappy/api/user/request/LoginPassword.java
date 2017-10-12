package com.behappy.api.user.request;


public class LoginPassword {

    private final String login;
    private final String password;

    public LoginPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
