package com.behappy.persistence.repository;

import com.behappy.persistence.entity.TherapyPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TherapyPersistenceRepository extends JpaRepository<TherapyPersistence,Long> {

    @Query(value = "SELECT t FROM TherapyPersistence t " +
                   "JOIN t.patientPersistence p " +
                   "JOIN p.user u " +
                   "WHERE u.email = :email")
    List<TherapyPersistence> findAllForPatient(@Param("email") String patientEmail);

    @Query(value = "SELECT t FROM TherapyPersistence t " +
                   "JOIN t.wardenPersistenceSet w " +
                   "JOIN w.user u " +
                   "WHERE u.email = :email")
    List<TherapyPersistence> findAllForWarden(@Param("email") String wardenEmail);

    @Query(value = "SELECT t FROM TherapyPersistence t " +
                   "JOIN t.therapistPersistenceSet th " +
                   "JOIN th.user u " +
                   "WHERE u.email = :email")
    List<TherapyPersistence> findAllForTherapist(@Param("email") String therapistEmail);
}

