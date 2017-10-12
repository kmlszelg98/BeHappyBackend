package com.behappy.persistence.dao.impl;

import com.behappy.domain.model.module.impl.MoodModule;
import com.behappy.domain.model.therapy.Mood;
import com.behappy.persistence.dao.IMoodModuleDao;
import com.behappy.persistence.entity.MoodModulePersistence;
import com.behappy.persistence.entity.MoodPersistence;
import com.behappy.persistence.entity.TherapyPersistence;
import com.behappy.persistence.repository.MoodModuleRepository;
import com.behappy.persistence.repository.TherapyPersistenceRepository;
import com.behappy.exceptions.NoSuchTherapyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoodModuleDao implements IMoodModuleDao {

    private final MoodModuleRepository moduleRepository;
    private final MoodMapper moodMapper;
    private final TherapyPersistenceRepository therapyRepository;

    @Autowired
    public MoodModuleDao(MoodModuleRepository moduleRepository, MoodMapper moodMapper, TherapyPersistenceRepository therapyRepository) {
        this.moduleRepository = moduleRepository;
        this.moodMapper = moodMapper;
        this.therapyRepository = therapyRepository;
    }

    @Override
    public MoodModule find(long therapyId){
        MoodModulePersistence persistence = moduleRepository.findByTherapyId(therapyId)
                .orElseThrow(() -> new NoSuchTherapyException("There's no module for therapy with given id"));
        return toMoodModule(persistence);
    }

    private MoodModule toMoodModule(MoodModulePersistence persistence){
        return new Builder()
                .withId(persistence.getId())
                .withActive(persistence.isActive())
                .withMoodList(moodMapper.toDomain(persistence.getMoodPersistenceList()))
                .withTherapyId(persistence.getTherapyPersistenceId())
                .buildDomain();
    }

    public void save(MoodModule module){
        moduleRepository.save(fromMoodModule(module));
    }

    @Override
    public void delete(long therapyId) {
        MoodModulePersistence persistence = moduleRepository.findByTherapyId(therapyId)
                .orElseThrow(() -> new NoSuchTherapyException("Module with this therapy id doesn't exists"));
        moduleRepository.delete(persistence);
    }

    private MoodModulePersistence fromMoodModule(MoodModule module){
        return new Builder()
                .withId(module.getId())
                .withActive(module.isActive())
                .withPersistenceList(moodMapper.toPersistence(module.getMoodList()))
                .withTherapyPersistence(therapyRepository.findOne(module.getTherapyId()))
                .buildPersistence();
    }

    private static class Builder{

        private long id;
        private List<Mood> moodList;
        private List<MoodPersistence> persistenceList;
        private TherapyPersistence therapyPersistence;
        private long therapyId;
        private boolean active;

        Builder(){}

        Builder withId(long id){
            this.id = id;
            return this;
        }

        Builder withMoodList(List<Mood> moodList){
            this.moodList = moodList;
            return this;
        }

        Builder withPersistenceList(List<MoodPersistence> persistenceList){
            this.persistenceList = persistenceList;
            return this;
        }

        Builder withTherapyPersistence(TherapyPersistence persistence){
            this.therapyPersistence = persistence;
            return this;
        }

        Builder withActive(boolean active){
            this.active = active;
            return this;
        }

        Builder withTherapyId(long therapyId){
            this.therapyId = therapyId;
            return this;
        }

        MoodModule buildDomain(){
            MoodModule moodModule = new MoodModule(id, therapyId, active);
            moodModule.setMoodList(moodList);
            return moodModule;
        }

        MoodModulePersistence buildPersistence(){
            MoodModulePersistence persistence = new MoodModulePersistence(persistenceList, active, therapyPersistence);
            persistence.setId(id);
            return persistence;
        }
    }
}
