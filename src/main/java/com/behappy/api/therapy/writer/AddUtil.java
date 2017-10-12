package com.behappy.api.therapy.writer;

import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.user.RoleEnum;
import com.behappy.domain.model.user.User;
import com.behappy.persistence.dao.impl.TherapyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddUtil {

    private final TherapyDao therapyDao;

    @Autowired
    public AddUtil(TherapyDao therapyDao) {
        this.therapyDao = therapyDao;
    }

    public boolean isCorrectToAdd(User user, RoleEnum role) {
        List<Therapy> therapies = therapyDao.findByEmail(user.getEmail());
        if (role == RoleEnum.PATIENT) {
            return isCorrectPatient(user.getEmail(), therapies);
        } else if (role == RoleEnum.WARDEN){
            return isCorrectWarden(user.getEmail(), therapies);
        } else {
            return isCorrectTherapist(user.getEmail(), therapies);
        }
    }

    private boolean isCorrectPatient(String email, List<Therapy> therapies) {
        return !isPatient(email, therapies)
                || !isTherapist(email, therapies);
    }


    private boolean isCorrectWarden(String email, List<Therapy> therapies) {
        return !isTherapist(email, therapies)
                && timesWarden(email, therapies) < 3;
    }

    private boolean isCorrectTherapist(String email, List<Therapy> therapies) {
        return !isPatientOrWarden(email, therapies)
                && timesTherapist(email, therapies) < 15;
    }

    private boolean isTherapist(String email, List<Therapy> therapies){
        for(Therapy t : therapies){
            if(t.getTherapistSet().stream().anyMatch(therapist -> therapist.getMail().equals(email))){
                return true;
            }
        }
        return false;
    }

    private boolean isPatientOrWarden(String email, List<Therapy> therapies){
        return isPatient(email, therapies)
                || timesWarden(email, therapies) != 0;
    }

    private boolean isPatient(String email, List<Therapy> therapies){
        for(Therapy therapy : therapies){
            if(therapy.getPatient().getMail().equals(email)){
                return true;
            }
        }
        return false;
    }

    private int timesWarden(String email, List<Therapy> therapies){
        int times = 0;
        for(Therapy therapy : therapies){
            if(therapy.getWardenSet().stream().anyMatch(w -> w.getMail().equals(email))){
                times++;
            }
        }
        return times;
    }

    private int timesTherapist(String email, List<Therapy> therapies){
        int times = 0;
        for(Therapy therapy : therapies){
            if(therapy.getTherapistSet().stream().anyMatch(t -> t.getMail().equals(email))){
                times++;
            }
        }
        return times;
    }
}