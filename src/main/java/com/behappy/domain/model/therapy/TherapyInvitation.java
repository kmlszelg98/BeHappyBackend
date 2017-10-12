package com.behappy.domain.model.therapy;

import com.behappy.domain.model.user.RoleEnum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Entity
public class TherapyInvitation {

    @Id
    private String uid;
    private long therapyId;
    private RoleEnum roleEnum;
    private String email;
    private String inviterMail;

    public TherapyInvitation(long therapyId, RoleEnum roleEnum , String email, String inviterMail) {
        uid = UUID.randomUUID().toString();
        this.therapyId = therapyId;
        this.roleEnum = roleEnum;
        this.email=email;
        this.inviterMail = inviterMail;
    }

    public TherapyInvitation(){}

    public String getId() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public long getTherapyId() {
        return therapyId;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public String getInviterMail() {return inviterMail;}

}
