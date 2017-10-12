package com.behappy.service.impl;

import com.behappy.domain.model.user.User;
import com.behappy.persistence.entity.NewsEventPersistence;
import com.behappy.persistence.dao.impl.NewsMapper;
import com.behappy.persistence.repository.NewsRepository;
import com.behappy.domain.model.news.NewsEvent;
import com.behappy.service.NewsService;
import com.behappy.service.TherapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private static final int NEWS_ON_PAGE = 5;

    private final NewsRepository newsRepository;
    private final TherapyService therapyService;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, TherapyService therapyService) {
        this.newsRepository = newsRepository;
        this.therapyService = therapyService;
    }

    @Override
    public Pair<List<NewsEvent>, Integer> getLatestNews(User user, int page_nr) {
        List<Long> usersTherapies = therapyService.getUserTherapiesID(user);
        List<NewsEventPersistence> allNews = newsRepository.findByTherapyIdInOrderByTimeDesc(usersTherapies);

        List<NewsEventPersistence> news = getNewsPage(page_nr, allNews);
        return Pair.of(NewsMapper.map(news), allNews.size());
    }

    private List<NewsEventPersistence> getNewsPage(int page_nr, List<NewsEventPersistence> allNews) {
        int lowerRange = (page_nr - 1) * NEWS_ON_PAGE;
        int upperRange = page_nr * NEWS_ON_PAGE;

        //Cast all news from persisted to their initial type
        if (lowerRange > allNews.size() || lowerRange < 0 || upperRange < 0) {
            throw new IllegalArgumentException("Not enough events");
        } else if (upperRange > allNews.size()) {
            upperRange = allNews.size();
        }

        return allNews.subList(lowerRange, upperRange);
    }

    @Override
    public Pair<List<NewsEvent>, Integer> getForTherapy(User user, long therapyId, int page_nr) {
        List<NewsEventPersistence> allNews = newsRepository.findByUserNameAndTherapyIdOrderByTimeDesc(user.getEmail(), therapyId);
        return Pair.of(NewsMapper.map(getNewsPage(page_nr, allNews)), allNews.size());
    }

    @Override
    public void publish(NewsEvent event) {
        newsRepository.save(NewsEventPersistence.fromEvent(event));
    }

}
