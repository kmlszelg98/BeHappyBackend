package com.behappy.api.therapy.request;

import com.behappy.domain.model.user.RoleEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@JsonDeserialize(builder = TherapyRequest.Builder.class)
public class TherapyRequest {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime beginningDate;

    @NotNull
    private RoleEnum role;

    @NotNull
    @Size(min = 1) // TODO: Think about potential max
    private String name;

    public TherapyRequest() {
    }

    public TherapyRequest(String name, LocalDateTime beginningDate, RoleEnum role) {
        this.name = name;
        this.beginningDate = beginningDate;
        this.role = role;
    }

    public TherapyRequest(Builder builder) {
        this.name = builder.name;
        this.beginningDate = builder.beginningDate;
        this.role = builder.role;
    }

    public RoleEnum getRole() {
        return role;
    }

    public LocalDateTime getBeginningDate() {
        return beginningDate;
    }

    public String getName() {
        return name;
    }

    @JsonPOJOBuilder(buildMethodName = "create", withPrefix = "set")
    public static final class Builder {
        private String name;
        private LocalDateTime beginningDate;
        private RoleEnum role;

        public Builder() {
        }

        public Builder setName(String name) {
            if (!name.isEmpty() && name.length() > 50) {
                throw new IllegalArgumentException();
            }
            this.name = name;
            return this;
        }

        public Builder setBeginningDate(LocalDateTime beginningDate) {
            this.beginningDate = beginningDate;
            return this;
        }

        public Builder setRole(RoleEnum role) {
            this.role = role;
            return this;
        }

        public TherapyRequest create() {
            return new TherapyRequest(this);
        }
    }
}
