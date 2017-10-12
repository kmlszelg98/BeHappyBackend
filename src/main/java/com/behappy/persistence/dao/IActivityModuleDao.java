package com.behappy.persistence.dao;

import com.behappy.domain.model.module.impl.ActivityModule;

public interface IActivityModuleDao {
    ActivityModule find(long therapyId);
    void save(ActivityModule module);
    void delete(long therapyId);
}
