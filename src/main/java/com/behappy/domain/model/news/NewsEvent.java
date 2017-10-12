package com.behappy.domain.model.news;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public interface NewsEvent {

    //Getters
    long getTherapyId();
    LocalDateTime getTime();
    String getUserName();
    String getTherapyName();
    String getDescription();
    EventType getEventType();
    Options getOptions();

    enum EventType {
        STATISTIC_FILLED_IN
    }

    class Options {
        private final Map<String, String> options;

        public Options(String options) {
            this.options = Arrays.stream(options.split(";"))
                    .map(s -> Arrays.asList(s.split(":")))
                    .collect(Collectors.toMap(a -> a.get(0), b -> b.get(1)));
        }

        public Options() {
            this.options = new HashMap<>();
        }

        public static Options create() {
            return new Options();
        }

        public Options put(String key, String value) {
            options.put(key, value);
            return this;
        }

        public String get(String key) {
            return options.get(key);
        }

        @Override
        public String toString() {
            return options.entrySet().stream()
                    .map(entry -> entry.getKey() + ":" + entry.getValue() + ";")
                    .reduce(String::concat).orElse("");
        }
    }
}
