package com.behappy.persistence.dao;

import com.behappy.domain.model.therapy.Therapy;

import java.util.List;

public interface ITherapyDao {
    Therapy find(long therapyId);
    List<Therapy> findByEmail(String email);
    void save(Therapy therapy);
    void delete(long therapyId);
}
