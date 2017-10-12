package com.behappy.persistence.dao.impl;

import com.behappy.domain.model.therapy.Patient;
import com.behappy.domain.model.therapy.Therapist;
import com.behappy.domain.model.therapy.Warden;
import com.behappy.domain.model.user.User;
import com.behappy.persistence.entity.PatientPersistence;
import com.behappy.persistence.entity.TherapistPersistence;
import com.behappy.persistence.entity.WardenPersistence;
import com.behappy.persistence.repository.UserRepository;
import com.behappy.exceptions.NoSuchUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class MemberMapper {

    private final UserRepository repository;

    @Autowired
    public MemberMapper(UserRepository repository) {
        this.repository = repository;
    }

    Patient getPatient(PatientPersistence persistence){
        return new Builder()
                .withId(persistence.getId())
                .withEmail(persistence.getEmail())
                .withActive(persistence.isActive())
                .withInviterMail(persistence.getInviterMail())
                .buildPatient();
    }

    PatientPersistence getPatientPersistence(Patient patient){
        return new Builder()
                .withId(patient.getId())
                .withUser(getUser(patient.getMail()))
                .withActive(patient.isActive())
                .withInviterMail(patient.getInviterMail())
                .buildPatientPersistence();
    }

    Therapist getTherapist(TherapistPersistence persistence){
        return new Builder()
                .withId(persistence.getId())
                .withEmail(persistence.getEmail())
                .withActive(persistence.isActive())
                .withInviterMail(persistence.getInviterMail())
                .buildTherapist();
    }

    TherapistPersistence getTherapistPersistence(Therapist therapist){
        return new Builder()
                .withId(therapist.getId())
                .withUser(getUser(therapist.getMail()))
                .withActive(therapist.isActive())
                .withInviterMail(therapist.getInviterMail())
                .buildTherapistPersistence();
    }

    Warden getWarden(WardenPersistence persistence){
        return new Builder()
                .withId(persistence.getId())
                .withEmail(persistence.getEmail())
                .withDateOfFlag(persistence.getDateOfFlag())
                .withActive(persistence.isActive())
                .withSpecial(persistence.isSpecial())
                .withInviterMail(persistence.getInviterMail())
                .buildWarden();
    }

    WardenPersistence getWardenPersistence(Warden warden){
        return new Builder()
                .withId(warden.getId())
                .withUser(getUser(warden.getMail()))
                .withSpecial(warden.isSpecial())
                .withActive(warden.isActive())
                .withDateOfFlag(warden.getDateOfFlag())
                .withInviterMail(warden.getInviterMail())
                .buildWardenPersistence();
    }

    private User getUser(String email){
        return repository.findOneByEmail(email)
                .orElseThrow(() -> new NoSuchUserException("There's no user"));
    }

    private static class Builder{
        private long id;
        private User user;
        private boolean special;
        private LocalDate dateOfFlag;
        private String email;
        private boolean active;
        private String inviterMail;

        Builder(){}

        Builder withId(long id){
            this.id = id;
            return this;
        }

        Builder withUser(User user){
            this.user = user;
            return this;
        }

        Builder withSpecial(boolean special){
            this.special = special;
            return this;
        }

        Builder withDateOfFlag(LocalDate dateOfFlag){
            this.dateOfFlag = dateOfFlag;
            return this;
        }

        Builder withEmail(String email){
            this.email = email;
            return this;
        }

        Builder withActive(boolean active){
            this.active = active;
            return this;
        }

        Builder withInviterMail(String mail) {
            this.inviterMail = mail;
            return this;
        }

        Patient buildPatient(){
            Patient patient = new Patient();
            patient.setId(id);
            patient.setMail(email);
            patient.setActive(active);
            patient.setInviterMail(inviterMail);
            return patient;
        }

        PatientPersistence buildPatientPersistence(){
            PatientPersistence persistence = new PatientPersistence();
            persistence.setId(id);
            persistence.setUser(user);
            persistence.setActive(active);
            persistence.setInviterMail(inviterMail);
            return persistence;
        }

        Warden buildWarden(){
            Warden warden = new Warden();
            warden.setId(id);
            warden.setMail(email);
            warden.setActive(active);
            warden.setDateOfFlag(dateOfFlag);
            if(special){
                warden.setToSpecial();
            } else {
                warden.setToCommon();
            }
            warden.setInviterMail(inviterMail);
            return warden;
        }

        WardenPersistence buildWardenPersistence(){
            WardenPersistence persistence = new WardenPersistence();
            persistence.setId(id);
            persistence.setUser(user);
            persistence.setActive(active);
            persistence.setDateOfFlag(dateOfFlag);
            persistence.setSpecial(special);
            persistence.setInviterMail(inviterMail);
            return persistence;
        }

        Therapist buildTherapist(){
            Therapist therapist = new Therapist();
            therapist.setId(id);
            therapist.setMail(email);
            therapist.setActive(active);
            therapist.setInviterMail(inviterMail);
            return therapist;
        }

        TherapistPersistence buildTherapistPersistence(){
            TherapistPersistence persistence = new TherapistPersistence();
            persistence.setId(id);
            persistence.setUser(user);
            persistence.setActive(active);
            persistence.setInviterMail(inviterMail);
            return persistence;
        }
    }

}
