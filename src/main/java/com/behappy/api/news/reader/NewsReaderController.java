package com.behappy.api.news.reader;

import com.behappy.api.news.response.NewsResponse;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.user.User;
import com.behappy.domain.model.news.NewsEvent;
import com.behappy.service.DiscoverService;
import com.behappy.service.NewsService;
import com.behappy.service.TherapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Bartosz on 24.12.2016.
 */
@RestController
public class NewsReaderController {

    private final NewsService newsService;

    private final DiscoverService discoverService;

    private final TherapyService therapyService;

    @Autowired
    public NewsReaderController(NewsService newsService, DiscoverService discoverService, TherapyService therapyService) {
        this.newsService = newsService;
        this.discoverService = discoverService;
        this.therapyService = therapyService;
    }

    @RequestMapping(value = "/api/news/{page_nr}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<NewsResponse> getNewsForUser(@PathVariable int page_nr) {
        User user = discoverService.getLoggedUser();
        Pair<List<NewsEvent>, Integer> newsWithCount = newsService.getLatestNews(user, page_nr);
        int count = newsWithCount.getSecond();
        return newsWithCount.getFirst().stream()
                .map(NewsResponse::fromEvent)
                .peek(newsResponse -> newsResponse.setCount(count))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/api/news/{therapy_id}/{page_nr}")
    @ResponseStatus(HttpStatus.OK)
    public List<NewsResponse> getNewsForTherapyID(@PathVariable long therapy_id, @PathVariable int page_nr) throws AuthenticationException {
        //TODO: Check user's privileges
        User user = discoverService.getLoggedUser();
        Therapy therapy = therapyService.getTherapy(therapy_id);

        if(!therapy.isUserActiveInTherapy(user)) {
            throw new AuthenticationException("Not allowed");
        }

        Pair<List<NewsEvent>, Integer> newsWithCount = newsService.getForTherapy(user, therapy_id, page_nr);
        int count = newsWithCount.getSecond();

        return newsWithCount.getFirst().stream()
                .map(NewsResponse::fromEvent)
                .peek(newsResponse -> newsResponse.setCount(count))
                .collect(Collectors.toList());
    }

}
