package com.behappy.persistence.repository;

import com.behappy.persistence.entity.ActivityModulePersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityModuleRepository extends JpaRepository<ActivityModulePersistence, Long>{
    Optional<ActivityModulePersistence> findByTherapyId(long therapyId);
}
