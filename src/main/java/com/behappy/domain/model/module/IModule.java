package com.behappy.domain.model.module;

public interface IModule {
    boolean isActive();
    long getId();
    long getTherapyId();

    void enable();
    void disable();
}
