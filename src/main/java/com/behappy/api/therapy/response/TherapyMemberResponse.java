package com.behappy.api.therapy.response;

import com.behappy.domain.model.therapy.Patient;
import com.behappy.domain.model.therapy.Therapist;
import com.behappy.domain.model.therapy.Warden;
import com.behappy.domain.model.user.RoleEnum;

public class TherapyMemberResponse {

    private String email;
    private String role;
    private boolean special = false;
    private boolean active;

    public TherapyMemberResponse(){}

    public TherapyMemberResponse(String email, RoleEnum role, boolean active) {
        this.email = email;
        this.role = role.toString();
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private void setSpecial(boolean special) {
        this.special = special;
    }

    public static TherapyMemberResponse fromPatient(Patient patient) {
        return new TherapyMemberResponse(patient.getMail(), RoleEnum.PATIENT, patient.isActive());
    }
    public static TherapyMemberResponse fromWarden(Warden warden) {
        TherapyMemberResponse wardenResponse = new TherapyMemberResponse(warden.getMail(), RoleEnum.WARDEN, warden.isActive());
        wardenResponse.setSpecial(warden.isSpecial());
        return wardenResponse;
    }
    public static TherapyMemberResponse fromTherapist(Therapist therapist) {
        return new TherapyMemberResponse(therapist.getMail(), RoleEnum.THERAPIST, therapist.isActive());
    }

}
