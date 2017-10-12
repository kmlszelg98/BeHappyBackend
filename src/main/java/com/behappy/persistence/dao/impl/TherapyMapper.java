package com.behappy.persistence.dao.impl;

import com.behappy.domain.model.therapy.Patient;
import com.behappy.domain.model.therapy.Therapist;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.therapy.Warden;
import com.behappy.persistence.entity.PatientPersistence;
import com.behappy.persistence.entity.TherapistPersistence;
import com.behappy.persistence.entity.TherapyPersistence;
import com.behappy.persistence.entity.WardenPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class TherapyMapper {

    private final MemberMapper memberMapper;

    @Autowired
    public TherapyMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    List<Therapy> getTherapies(List<TherapyPersistence> persistenceList){
        return persistenceList.stream().map(this::getTherapy).collect(Collectors.toList());
    }

    List<TherapyPersistence> getPersistenceTherapies(List<Therapy> therapies){
        return therapies.stream().map(this::getTherapyPersistence).collect(Collectors.toList());
    }

    Therapy getTherapy(TherapyPersistence persistence){
        return new Builder()
                .withId(persistence.getId())
                .withName(persistence.getName())
                .withBeginning(persistence.getBeginningDate())
                .withCreatorMail(persistence.getCreatorMail())
                .withPatient(memberMapper.getPatient(persistence.getPatientPersistence()))
                .withWardenSet(getWardenSet(persistence.getWardenPersistenceSet()))
                .withTherapistSet(getTherapistSet(persistence.getTherapistPersistenceSet()))
                .buildTherapy();
    }

    TherapyPersistence getTherapyPersistence(Therapy therapy){
        return new Builder()
                .withId(therapy.getId())
                .withCreatorMail(therapy.getCreator())
                .withName(therapy.getName())
                .withBeginning(therapy.getBeginningDate())
                .withPersistencePatient(memberMapper.getPatientPersistence(therapy.getPatient()))
                .withPersistenceWardenSet(getPersistenceWardenSet(therapy.getWardenSet()))
                .withPersistenceTherapistSet(getPersistenceTherapistSet(therapy.getTherapistSet()))
                .buildTherapyPersistence();
    }

    private Set<Warden> getWardenSet(Set<WardenPersistence> persistence){
        return persistence.stream().map(memberMapper::getWarden).collect(Collectors.toSet());
    }

    private Set<WardenPersistence> getPersistenceWardenSet(Set<Warden> set){
        return set.stream().map(memberMapper::getWardenPersistence).collect(Collectors.toSet());
    }

    private Set<Therapist> getTherapistSet(Set<TherapistPersistence> persistence){
        return persistence.stream().map(memberMapper::getTherapist).collect(Collectors.toSet());
    }

    private Set<TherapistPersistence> getPersistenceTherapistSet(Set<Therapist> set){
        return set.stream().map(memberMapper::getTherapistPersistence).collect(Collectors.toSet());
    }

    private static class Builder {
        private long id;
        private String name;
        private LocalDateTime beginningDate;
        private String creatorMail;
        private Patient patient;
        private PatientPersistence patientPersistence;
        private Set<Warden> wardenSet;
        private Set<WardenPersistence> wardenPerSet;
        private Set<Therapist> therapistSet;
        private Set<TherapistPersistence> therapistPerSet;

        Builder(){}

        Builder withId(long id){
            this.id = id;
            return this;
        }

        Builder withName(String name){
            this.name = name;
            return this;
        }

        Builder withBeginning(LocalDateTime date){
            this.beginningDate = date;
            return this;
        }

        Builder withCreatorMail(String mail){
            this.creatorMail = mail;
            return this;
        }

        Builder withPatient(Patient patient){
            this.patient = patient;
            return this;
        }

        Builder withPersistencePatient(PatientPersistence persistence){
            this.patientPersistence = persistence;
            return this;
        }

        Builder withWardenSet(Set<Warden> wardenSet){
            this.wardenSet = wardenSet;
            return this;
        }

        Builder withPersistenceWardenSet(Set<WardenPersistence> persistenceSet){
            this.wardenPerSet = persistenceSet;
            return this;
        }

        Builder withTherapistSet(Set<Therapist> therapistSet){
            this.therapistSet = therapistSet;
            return this;
        }

        Builder withPersistenceTherapistSet(Set<TherapistPersistence> persistenceSet){
            this.therapistPerSet = persistenceSet;
            return this;
        }

        Therapy buildTherapy(){
            Therapy therapy = new Therapy(id, name, beginningDate, creatorMail);
            therapy.setPatient(patient);
            therapy.setWardenSet(wardenSet);
            therapy.setTherapistSet(therapistSet);
            return therapy;
        }

        TherapyPersistence buildTherapyPersistence(){
            TherapyPersistence persistence = new TherapyPersistence();
            persistence.setId(id);
            persistence.setCreatorMail(creatorMail);
            persistence.setBeginningDate(beginningDate);
            persistence.setName(name);
            persistence.setPatientPersistence(patientPersistence);
            persistence.setTherapistPersistenceSet(therapistPerSet);
            persistence.setWardenPersistenceSet(wardenPerSet);
            return persistence;
        }
    }
}
