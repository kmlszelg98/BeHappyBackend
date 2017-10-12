package com.behappy.service.impl;

import com.behappy.domain.model.therapy.TherapyInvitation;
import com.behappy.persistence.repository.InvitationRepository;
import com.behappy.exceptions.NoSuchInvitationException;
import com.behappy.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    @Autowired
    InvitationServiceImpl(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @Override
    public void add(TherapyInvitation invitation) {
        if (!isAlreadyInvited(invitation)) {
            invitationRepository.save(invitation);
        }
    }

    private boolean isAlreadyInvited(TherapyInvitation invitation){
        return invitationRepository.findOneByEmailAndTherapyId(invitation.getEmail(), invitation.getTherapyId())
                .isPresent();
    }

    @Override
    public TherapyInvitation find(String uid) {
        TherapyInvitation output = invitationRepository.findOne(uid);
        if (output == null) {
            throw new NoSuchInvitationException("No matching invitation found");
        }
        return output;
    }
    @Override
    public void remove(String uid){
        invitationRepository.delete(uid);
    }
}
