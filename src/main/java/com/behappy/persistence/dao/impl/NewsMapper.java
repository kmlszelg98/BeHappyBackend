package com.behappy.persistence.dao.impl;

import com.behappy.persistence.entity.NewsEventPersistence;
import com.behappy.domain.model.news.NewsEvent;
import com.behappy.domain.model.news.StatisticFilledInEvent;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bartosz on 11.02.17.
 */
public class NewsMapper {
    public static List<NewsEvent> map(Collection<NewsEventPersistence> news) {
        return news.stream()
                .map(n -> {
                    switch (n.getEventType()) {
                        case STATISTIC_FILLED_IN:
                            return StatisticFilledInEvent.fromPersistence(n);
                        default:
                            return n;
                    }
                })
                .filter(n -> !(n instanceof NewsEventPersistence))
                .collect(Collectors.toList());
    }
}
