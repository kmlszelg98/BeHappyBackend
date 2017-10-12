package com.behappy.service;

import com.behappy.domain.model.user.User;
import com.behappy.domain.model.news.NewsEvent;
import org.springframework.data.util.Pair;

import java.util.List;

/**
 * Created by Bartosz on 24.12.2016.
 */
public interface NewsService {

    //READ
    Pair<List<NewsEvent>, Integer> getLatestNews(User user, int page_nr);

    Pair<List<NewsEvent>, Integer> getForTherapy(User user, long therapyId, int page_nr);


    //WRITE
    void publish(NewsEvent event);
}
