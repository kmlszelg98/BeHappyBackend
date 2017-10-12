package com.behappy.persistence.repository;

import com.behappy.persistence.entity.MoodModulePersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoodModuleRepository extends JpaRepository<MoodModulePersistence, Long> {
    Optional<MoodModulePersistence> findByTherapyId(long therapyId);
}
