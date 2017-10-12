package com.behappy.persistence.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "therapies_table")
public class TherapyPersistence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "therapy_id")
    private long id;

    private String name;

    private LocalDateTime beginningDate;

    private String creatorMail;

    //TODO: Fix (therapy_id FK in patients table)
    @OneToOne(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private PatientPersistence patientPersistence;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "therapy_id")
    private Set<WardenPersistence> wardenPersistenceSet;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "therapy_id")
    private Set<TherapistPersistence> therapistPersistenceSet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(LocalDateTime beginningDate) {
        this.beginningDate = beginningDate;
    }

    public String getCreatorMail() {
        return creatorMail;
    }

    public void setCreatorMail(String creatorMail) {
        this.creatorMail = creatorMail;
    }

    public PatientPersistence getPatientPersistence() {
        return patientPersistence;
    }

    public void setPatientPersistence(PatientPersistence patientPersistence) {
        this.patientPersistence = patientPersistence;
    }

    public Set<WardenPersistence> getWardenPersistenceSet() {
        return wardenPersistenceSet;
    }

    public void setWardenPersistenceSet(Set<WardenPersistence> wardenPersistenceSet) {
        this.wardenPersistenceSet = wardenPersistenceSet;
    }

    public Set<TherapistPersistence> getTherapistPersistenceSet() {
        return therapistPersistenceSet;
    }

    public void setTherapistPersistenceSet(Set<TherapistPersistence> therapistPersistenceSet) {
        this.therapistPersistenceSet = therapistPersistenceSet;
    }
}
