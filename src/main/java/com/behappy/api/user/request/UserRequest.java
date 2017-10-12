package com.behappy.api.user.request;

import com.behappy.domain.model.user.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonDeserialize(builder = UserRequest.Builder.class)
public class UserRequest {

    @NotNull
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @NotNull
    @Size(min = 6, max = 50)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    public UserRequest(){}

    public UserRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserRequest(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.name = builder.name;
    }

    public User getUser() {
        // Null gender - not required at registration
        return new User(email, password, null, name);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {return name;}

    @JsonPOJOBuilder(buildMethodName = "create", withPrefix = "set")
    public static final class Builder {
        private String email;
        private String password;
        private String name;

        public Builder() {
        }

        public Builder setEmail(String email) {
            if (!email.isEmpty() && !email.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")) {
                throw new IllegalArgumentException();
            }
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            if (password.length() != 0 && password.length() < 6) {
                throw new IllegalArgumentException();
            }
            this.password = password;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public UserRequest create() {
            return new UserRequest(this);
        }
    }
}