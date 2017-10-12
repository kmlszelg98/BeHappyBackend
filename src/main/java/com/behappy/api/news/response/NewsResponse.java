package com.behappy.api.news.response;

import com.behappy.domain.model.news.NewsEvent;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class NewsResponse {
    private long therapyId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime time;
    private String userName;
    private String therapyName;

    private String description;

    private int count;

    public NewsResponse(long therapyId,
                        LocalDateTime time,
                        String userName,
                        String therapyName,
                        String description) {
        this.therapyId = therapyId;
        this.time = time;
        this.userName = userName;
        this.therapyName = therapyName;
        this.description = description;
    }

    public static NewsResponse fromEvent(NewsEvent newsEvent) {
        long therapyId = newsEvent.getTherapyId();
        LocalDateTime time = newsEvent.getTime();
        String userName = newsEvent.getUserName();
        String therapyName = newsEvent.getTherapyName();
        String description = newsEvent.getDescription();
        return new NewsResponse(therapyId, time, userName, therapyName, description);
    }

    public long getTherapyId() {
        return therapyId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getUserName() {
        return userName;
    }

    public String getTherapyName() {
        return therapyName;
    }

    public String getDescription() {
        return description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
