package com.behappy.persistence.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class ActivityModulePersistence {

    @Id
    @Column(name = "module_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "activity_id")
    private List<ActivityPersistence> activityPersistenceList;

    private boolean active;

    @OneToOne(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "therapy_id")
    private TherapyPersistence therapyId;

    public ActivityModulePersistence(){}

    public ActivityModulePersistence(List<ActivityPersistence> activityPersistenceList,
                                     boolean active,
                                     TherapyPersistence therapyId) {
        this.activityPersistenceList = activityPersistenceList;
        this.active = active;
        this.therapyId = therapyId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ActivityPersistence> getActivityPersistenceList() {
        return activityPersistenceList;
    }

    public void setActivityPersistenceList(List<ActivityPersistence> activityPersistenceList) {
        this.activityPersistenceList = activityPersistenceList;
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
