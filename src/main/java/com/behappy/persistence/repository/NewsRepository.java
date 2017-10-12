package com.behappy.persistence.repository;

import com.behappy.persistence.entity.NewsEventPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEventPersistence, Long> {
    List<NewsEventPersistence> findByTherapyIdInOrderByTimeDesc(List<Long> therapyId);

    List<NewsEventPersistence> findByUserNameAndTherapyIdOrderByTimeDesc(String userName, long therapyId);
}
