package com.behappy.domain.model.module;

public abstract class Module implements IModule {

    private long id;
    private boolean active = true;
    private long therapyId;

    public Module(){}

    public Module(long therapyId){
        this.therapyId = therapyId;
    }

    public Module(long id, long therapyId){
        this.id = id;
        this.therapyId = therapyId;
    }

    public Module(long id, long therapyId, boolean active){
        this(id, therapyId);
        this.active = active;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public long getTherapyId() {
        return therapyId;
    }

    @Override
    public void enable(){
        this.active = true;
    }

    @Override
    public void disable(){
        this.active = false;
    }
}
