package com.behappy.persistence.dao.impl;

import com.behappy.domain.model.module.impl.ActivityModule;
import com.behappy.domain.model.therapy.Activity;
import com.behappy.exceptions.NoSuchTherapyException;
import com.behappy.persistence.dao.IActivityModuleDao;
import com.behappy.persistence.entity.ActivityModulePersistence;
import com.behappy.persistence.entity.ActivityPersistence;
import com.behappy.persistence.entity.TherapyPersistence;
import com.behappy.persistence.repository.ActivityModuleRepository;
import com.behappy.persistence.repository.TherapyPersistenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityModuleDao implements IActivityModuleDao {

    private final ActivityMapper activityMapper;
    private final ActivityModuleRepository moduleRepository;
    private final TherapyPersistenceRepository therapyRepository;

    @Autowired
    public ActivityModuleDao(ActivityModuleRepository moduleRepository,
                             ActivityMapper activityMapper, TherapyPersistenceRepository therapyRepository) {
        this.moduleRepository = moduleRepository;
        this.activityMapper = activityMapper;
        this.therapyRepository = therapyRepository;
    }

    @Override
    public ActivityModule find(long therapyId){
        ActivityModulePersistence modulePersistence = moduleRepository.findByTherapyId(therapyId)
                .orElseThrow(() -> new NoSuchTherapyException("There's no such module"));
        return toActivityModule(modulePersistence);
    }

    private ActivityModule toActivityModule(ActivityModulePersistence modulePersistence){
        return new Builder()
                .withId(modulePersistence.getId())
                .withActivityList(activityMapper.toDomain(modulePersistence.getActivityPersistenceList()))
                .withActive(modulePersistence.isActive())
                .withTherapyId(modulePersistence.getTherapyPersistenceId())
                .buildActivityModule();
    }

    @Override
    public void save(ActivityModule module) {
        moduleRepository.save(fromActivityModule(module));
    }

    @Override
    public void delete(long therapyId) {
        ActivityModulePersistence persistence = moduleRepository.findByTherapyId(therapyId)
                .orElseThrow(() -> new NoSuchTherapyException("Module with this therapy id doesn't exists"));
        moduleRepository.delete(persistence);
    }

    private ActivityModulePersistence fromActivityModule(ActivityModule module){
        return new Builder()
                .withId(module.getId())
                .withActive(module.isActive())
                .withPersistenceList(activityMapper.toPersistence(module.getActivityList()))
                .withTherapyPersistence(therapyRepository.findOne(module.getTherapyId()))
                .buildPersistenceModule();
    }

    private static class Builder {
        private long id;
        private List<ActivityPersistence> persistenceList;
        private List<Activity> activityList;
        boolean active;
        private TherapyPersistence therapyPersistence;
        private long therapyId;

        Builder(){}

        Builder withId(long id){
            this.id = id;
            return this;
        }

        Builder withPersistenceList(List<ActivityPersistence> persistenceList){
            this.persistenceList = persistenceList;
            return this;
        }

        Builder withActivityList(List<Activity> activityList){
            this.activityList = activityList;
            return this;
        }

        Builder withActive(boolean active){
            this.active = active;
            return this;
        }

        Builder withTherapyPersistence(TherapyPersistence persistence){
            this.therapyPersistence = persistence;
            return this;
        }

        Builder withTherapyId(long therapyId){
            this.therapyId = therapyId;
            return this;
        }

        ActivityModule buildActivityModule(){
            ActivityModule module = new ActivityModule(id, therapyId, active);
            module.setActivityList(activityList);
            return module;
        }

        ActivityModulePersistence buildPersistenceModule(){
            ActivityModulePersistence persistence = new ActivityModulePersistence(persistenceList, active, therapyPersistence);
            persistence.setId(id);
            return persistence;
        }
    }
}
