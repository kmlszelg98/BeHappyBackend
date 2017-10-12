package com.behappy.persistence.repository;

import com.behappy.domain.model.therapy.TherapyInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InvitationRepository extends JpaRepository<TherapyInvitation, String> {
    Optional<TherapyInvitation> findOneByEmailAndTherapyId(String email, long therapy_id);
}
