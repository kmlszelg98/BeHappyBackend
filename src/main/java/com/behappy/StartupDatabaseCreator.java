package com.behappy;

import com.behappy.domain.model.module.impl.MoodModule;
import com.behappy.domain.model.therapy.Mood;
import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.user.RoleEnum;
import com.behappy.domain.model.user.User;
import com.behappy.persistence.dao.impl.MoodModuleDao;
import com.behappy.persistence.dao.impl.TherapyDao;
import com.behappy.persistence.repository.UserRepository;
import com.behappy.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class StartupDatabaseCreator implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final TherapyDao therapyDao;
    private final NewsService newsService;
    private final MoodModuleDao moodModuleDao;


    @Autowired
    public StartupDatabaseCreator(UserRepository userRepository,
                                  TherapyDao therapyDao,
                                  NewsService newsService,
                                  MoodModuleDao moodModuleDao) {
        this.userRepository = userRepository;
        this.therapyDao = therapyDao;
        this.newsService = newsService;
        this.moodModuleDao = moodModuleDao;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(!isDatabaseEmpty()) {
            return;
        }
        User user = new User("user1@mail.com", "password");
        User user2 = new User("user2@gmail.com", "password");
        User user3 = new User("user3@gmail.com", "password");

        User wardenUser1 = new User("warden1@gmail.com", "password");
        User wardenUser2 = new User("warden2@gmail.com", "password");

        User therapistUser = new User("therapist1@gmail.com", "password");

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        userRepository.save(wardenUser1);
        userRepository.save(wardenUser2);

        userRepository.save(therapistUser);

        Therapy therapy1 = new Therapy(user.getEmail(), "Therapy1", LocalDateTime.of(2017, 1, 5, 12, 12));
        Therapy therapy2 = new Therapy(user2.getEmail(), "Therapy2", LocalDateTime.of(2017, 1, 5, 13, 13));
        Therapy therapy3 = new Therapy(user3.getEmail(), "Therapy3", LocalDateTime.of(2017, 1, 5, 14, 14));
        Therapy therapy4 = new Therapy(user.getEmail(), "Therapy4", LocalDateTime.of(2017, 1, 6, 12, 12));

        therapy1.addUser(user, RoleEnum.PATIENT);
        therapy2.addUser(user2, RoleEnum.PATIENT);
        therapy3.addUser(user3, RoleEnum.PATIENT);
        therapy4.addUser(user, RoleEnum.PATIENT);

        therapy1.addUser(wardenUser1, RoleEnum.WARDEN);
        therapy1.activateUser(wardenUser1.getEmail(), user.getEmail());

        therapy2.addUser(wardenUser2, RoleEnum.WARDEN);
        therapy2.activateUser(wardenUser2.getEmail(), user2.getEmail());

        therapy4.addUser(therapistUser, RoleEnum.THERAPIST);
        therapy4.activateUser(therapistUser.getEmail(), user.getEmail());

        therapy2.assignSpecial(wardenUser2.getEmail());

        therapyDao.save(therapy1);
        therapyDao.save(therapy2);
        therapyDao.save(therapy3);

        Mood mood1 = new Mood(LocalDate.of(2017, 1, 7), 7, 7, "Automatic thought 1");
        Mood mood2 = new Mood(LocalDate.of(2017, 1, 8), 10, 10, "Automatic thought 2");
        Mood mood3 = new Mood(LocalDate.of(2017, 1, 9), 10, 9, "Automatic thought 3");

        MoodModule moodModule = new MoodModule(therapy1.getId());
        moodModule.addMood(mood1);
        moodModule.addMood(mood2);
        moodModule.addMood(mood3);

        moodModuleDao.save(moodModule);
    }

    private boolean isDatabaseEmpty(){
        return userRepository.findAll().size() == 0;
    }
}
