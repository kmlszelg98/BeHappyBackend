package com.behappy.persistence.dao.impl;

import com.behappy.domain.model.therapy.Therapy;
import com.behappy.exceptions.NoSuchTherapyException;
import com.behappy.persistence.dao.ITherapyDao;
import com.behappy.persistence.entity.TherapyPersistence;
import com.behappy.persistence.repository.TherapyPersistenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class TherapyDao implements ITherapyDao {

    private final TherapyPersistenceRepository therapyRepository;
    private final TherapyMapper mapper;
    private final MoodModuleDao moodModuleDao;
    private final ActivityModuleDao activityModuleDao;

    @Autowired
    public TherapyDao(TherapyPersistenceRepository therapyRepository,
                      TherapyMapper mapper,
                      MoodModuleDao moodModuleDao,
                      ActivityModuleDao activityModuleDao) {
        this.therapyRepository = therapyRepository;
        this.mapper = mapper;
        this.moodModuleDao = moodModuleDao;
        this.activityModuleDao = activityModuleDao;
    }

    @Override
    public void save(Therapy therapy) {
        therapyRepository.save(mapper.getTherapyPersistence(therapy));
    }

    @Override
    public void delete(long therapyId) {
        therapyRepository.delete(therapyId);
        moodModuleDao.delete(therapyId);
        activityModuleDao.delete(therapyId);
    }

    @Override
    public Therapy find(long therapyId){
        TherapyPersistence persistence = therapyRepository.findOne(therapyId);
        if(persistence == null){
            throw new NoSuchTherapyException("There's no such therapy");
        }
        return mapper.getTherapy(persistence);
    }

    @Override
    public List<Therapy> findByEmail(String email) {
        List<TherapyPersistence> persistenceList = new LinkedList<>();
        persistenceList.addAll(therapyRepository.findAllForPatient(email));
        persistenceList.addAll(therapyRepository.findAllForTherapist(email));
        persistenceList.addAll(therapyRepository.findAllForWarden(email));
        return mapper.getTherapies(persistenceList);
    }
}