package com.behappy.domain.model.therapy;

import com.behappy.domain.model.user.RoleEnum;
import com.behappy.domain.model.user.User;
import com.behappy.domain.settings.Settings;
import com.behappy.exceptions.NoSuchUserException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;


public class Therapy {

    private long id;
    private String name;
    private LocalDateTime beginningDate;
    private String creatorMail;
    private Patient patient;
    private Set<Warden> wardenSet;
    private Set<Therapist> therapistSet;

    public Therapy(String creatorMail, String name, LocalDateTime beginningDate) {
        this.name = name;
        this.beginningDate = beginningDate;
        this.creatorMail = creatorMail;

        wardenSet = new HashSet<>();
        therapistSet = new HashSet<>();
    }

    public Therapy(long id, String name, LocalDateTime beginningDate, String creatorMail) {
        this(creatorMail, name, beginningDate);
        this.id = id;
    }


    public long getId() {
        return id;
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

    public Set<Warden> getWardenSet() {
        return wardenSet;
    }

    public Set<Therapist> getTherapistSet() {
        return therapistSet;
    }

    public String getCreator() {
        return creatorMail;
    }

    public int getNumOfTherapist() {
        return therapistSet.size();
    }

    public int getNumOfWardens() {
        return wardenSet.size();
    }

    public int getNumOfPatients() { return patient == null ? 0 : 1; }

    public Patient getPatient() {
        return patient;
    }

    public void setWardenSet(Set<Warden> wardenSet) {
        this.wardenSet = wardenSet;
    }

    public void setTherapistSet(Set<Therapist> therapistSet) {
        this.therapistSet = therapistSet;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isUserActiveInTherapy(User user){
        return isActiveInTherapy(user.getEmail());
    }

    private boolean isActiveInTherapy(String email){
        if(patient.getMail().equals(email)){
            return patient.isActive();
        }
        return wardenSet.stream().filter(w -> w.getMail().equals(email)).anyMatch(Member::isActive)
                || therapistSet.stream().filter(t -> t.getMail().equals(email)).anyMatch(Member::isActive);
    }

    public void removeUser(String email){
        if(patient.getMail().equals(email)) {
            patient = null;
        }
        wardenSet.removeIf(w -> email.equals(w.getMail()));
        therapistSet.removeIf(t -> email.equals(t.getMail()));
    }

    public void assignSpecial(String email){
        Warden warden = getSpecialWarden()
                .orElse(assignNewSpecial(email));
        if(!warden.getMail().equals(email)){
            warden.setToCommon();
            assignNewSpecial(email);
        }
    }

    private Warden assignNewSpecial(String email){
        return wardenSet.stream()
                .filter(w -> email.equals(w.getMail()))
                .peek(Warden::setToSpecial)
                .findFirst()
                .orElseThrow( () -> new DataIntegrityViolationException("This warden isn't in this therapy"));
    }

    private Optional<Warden> getSpecialWarden(){
        return wardenSet.stream()
                .filter(Warden::isSpecial)
                .findFirst();
    }

    public boolean isCreator(String email){
        return creatorMail.equals(email);
    }

    public boolean isSpecial(String email){
        return wardenSet.stream()
                .filter(Warden::isSpecial)
                .findFirst()
                .filter(w -> w.getMail().equals(email))
                .isPresent();
    }

    public RoleEnum discoverRole(User user){

        if(patient.getMail().equals(user.getEmail())){
            return RoleEnum.PATIENT;
        }

        if(therapistSet.stream().anyMatch(t -> t.getMail().equals(user.getEmail()))){
            return RoleEnum.THERAPIST;
        }

        if(wardenSet.stream().anyMatch(w -> w.getMail().equals(user.getEmail()))){
            return RoleEnum.WARDEN;
        }

        throw new NoSuchUserException("No such user in given therapy");
    }

    public boolean isRoleFree(RoleEnum role) {
        if(role == RoleEnum.PATIENT){
            return patient == null;
        } else if (role == RoleEnum.WARDEN) {
            return getNumOfWardens() < Settings.MAX_WARDENS;
        } else if (role == RoleEnum.THERAPIST){
            return getNumOfTherapist() < Settings.MAX_THERAPISTS;
        }
        return false;
    }

    public void addUser(User loggedUser, RoleEnum role) {
        addUserToTherapy(loggedUser, role);
        if(loggedUser.getEmail().equals(creatorMail)){
            activateUser(creatorMail, creatorMail); // Creator has inviter mail set to himself for simplicity
        }
    }

    //It will add with default active to false flag
    private void addUserToTherapy(User user, RoleEnum roleEnum){
        switch(roleEnum){
            case PATIENT:
                patient = Patient.fromUser(user);
                break;
            case THERAPIST:
                therapistSet.add(Therapist.fromUser(user));
                break;
            case WARDEN:
                wardenSet.add(Warden.fromUser(user));
        }
    }

    public void activateUser(String email, String inviterMail){
        if(patient.getMail().equals(email)) {
            patient.setActive(true);
            patient.setInviterMail(inviterMail);
        }
        for(Therapist t : therapistSet) {
            if(t.getMail().equals(email)) {
                t.setActive(true);
                t.setInviterMail(inviterMail);
            }
        }
        for(Warden w : wardenSet) {
            if(w.getMail().equals(email)) {
                w.setActive(true);
                w.setInviterMail(inviterMail);
            }
        }
    }

    public boolean isUserInTherapy(User user) {
        String email = user.getEmail();
        return patient.getMail().equals(email)
                || wardenSet.stream().anyMatch(w -> w.getMail().equals(email))
                || therapistSet.stream().anyMatch(t -> t.getMail().equals(email));
    }

    public String getInviterMailOf(String email) {
        if(patient != null && patient.getMail().equals(email)) {
            return patient.getInviterMail();
        }
        // If we invoke this method then a member with given mail exists, thus get() without ifPresent
        return Stream.of(wardenSet, therapistSet)
                .filter(Objects::nonNull)
                .flatMap(Set::stream)
                .filter(member -> member.getMail().equals(email))
                .findFirst()
                .get()
                .getInviterMail();
    }
}
