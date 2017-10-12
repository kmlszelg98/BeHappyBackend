package com.behappy.domain.model.news;

import com.behappy.domain.model.therapy.Therapy;
import com.behappy.domain.model.therapy.Mood;
import com.behappy.domain.model.user.User;
import com.behappy.persistence.entity.NewsEventPersistence;

import java.time.LocalDateTime;

public class StatisticFilledInEvent implements NewsEvent {
    private static final String DESCR_PL = "%s: Pacjent %s uzupełnił samopoczucie z dnia %s na %s";
    private final long therapyId;
    //todo decide it should be immutable or not
    private LocalDateTime time;
    private final String userName;
    private final String therapyName;
    private final EventType eventType;

    private final Options options;

    public StatisticFilledInEvent(Therapy therapy, User user, Mood statistic) {
        this.time = LocalDateTime.now();
        this.therapyId = therapy.getId();
        this.therapyName = therapy.getName();
        this.userName = user.getEmail();
        this.eventType = EventType.STATISTIC_FILLED_IN;
        this.options = Options.create()
                .put("DATE", statistic.getDate().toString())
                .put("MARK", String.valueOf(statistic.getMark()))
                .put("FEARS", String.valueOf(statistic.getFear()));
    }

    public StatisticFilledInEvent(long id,
                                  LocalDateTime time,
                                  long therapyId, String userName,
                                  String therapyName,
                                  EventType eventType,
                                  Options options) {
        this.time = time;
        this.therapyId = therapyId;
        this.userName = userName;
        this.therapyName = therapyName;
        this.eventType = eventType;
        this.options = options;
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
    public long getTherapyId() {
        return therapyId;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public String getDescription() {
        String result = String.format(DESCR_PL, therapyName, userName, options.get("DATE"), options.get("MARK"));
        Integer fears = Integer.valueOf(options.get("FEARS"));
        if (fears != 0) {
            result += String.format("\nPacjent odczuwał w ten dzień lęki na poziomie %d", fears);
        } else {
            result += "\nPacjent nie odczuwał w ten dzień lęków.";
        }
        return result;
    }

    public static StatisticFilledInEvent fromPersistence(NewsEventPersistence persistence) {
        return new StatisticFilledInEvent(
                persistence.getId(),
                persistence.getTime(),
                persistence.getTherapyId(),
                persistence.getUserName(),
                persistence.getTherapyName(),
                persistence.getEventType(),
                persistence.getOptions()
        );
    }
}
