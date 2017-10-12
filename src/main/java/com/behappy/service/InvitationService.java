package com.behappy.service;

import com.behappy.domain.model.therapy.TherapyInvitation;
import org.springframework.stereotype.Service;

@Service
public interface InvitationService {
    void add(TherapyInvitation therapyInvitation);
    TherapyInvitation find(String uid);
    void remove(String uid);
}
