package com.behappy.persistence.dao;

import com.behappy.domain.model.module.impl.MoodModule;

public interface IMoodModuleDao {
    MoodModule find(long therapyId);
    void save(MoodModule module);
    void delete(long therapyId);
}
