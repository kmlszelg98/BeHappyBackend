package com.behappy.persistence.entity;

import com.behappy.domain.model.news.NewsEvent;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
public class NewsEventPersistence implements NewsEvent {

    @Id
    @Column(name = "news_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long therapyId;
    private LocalDateTime time;
    private String userName;
    private String therapyName;
    private String eventType;
    private String options;

    private NewsEventPersistence() {
    }

    private NewsEventPersistence(long therapyId, LocalDateTime time, String userName, String therapyName, String eventType, String options) {
        this.therapyId = therapyId;
        this.time = time;
        this.userName = userName;
        this.therapyName = therapyName;
        this.options = options;
        this.eventType = eventType;
    }

    public static NewsEventPersistence fromEvent(NewsEvent newsEvent) {
        long therapyId = newsEvent.getTherapyId();
        LocalDateTime time = newsEvent.getTime();
        String userName = newsEvent.getUserName();
        String therapyName = newsEvent.getTherapyName();
        String options = newsEvent.getOptions().toString();
        String eventType = newsEvent.getEventType().toString();
        return new NewsEventPersistence(therapyId, time, userName, therapyName, eventType, options);
    }

    public long getId(){
        return id;
    }

    @Override
    public long getTherapyId() {
        return therapyId;
    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getTherapyName() {
        return therapyName;
    }

    @Override
    public EventType getEventType() {
        return EventType.valueOf(eventType);
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Options getOptions() {
        return new Options(options);
    }
}