package com.behappy.persistence.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mood_modules")
public class MoodModulePersistence {

    @Id
    @Column(name = "module_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "mood_module_id")
    private List<MoodPersistence> moodPersistenceList;

    private boolean active;

    @OneToOne(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "therapy_id")
    private TherapyPersistence therapyId;

    public MoodModulePersistence() {
    }

    public MoodModulePersistence(List<MoodPersistence> moodPersistenceList,
                                 boolean active,
                                 TherapyPersistence therapyId) {
        this.moodPersistenceList = moodPersistenceList;
        this.active = active;
        this.therapyId = therapyId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<MoodPersistence> getMoodPersistenceList() {
        return moodPersistenceList;
    }

    public void setMoodPersistenceList(List<MoodPersistence> moodPersistenceList) {
        this.moodPersistenceList = moodPersistenceList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public TherapyPersistence getTherapyId() {
        return therapyId;
    }

    public void setTherapyId(TherapyPersistence therapyId) {
        this.therapyId = therapyId;
    }

    public long getTherapyPersistenceId(){
        return getTherapyId().getId();
    }
}
