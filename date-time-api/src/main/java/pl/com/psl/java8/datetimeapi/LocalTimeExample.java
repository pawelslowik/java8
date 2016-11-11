package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * Created by psl on 02.11.16.
 */
public class LocalTimeExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalTimeExample.class);

    public static void main(String[] args) {
        LOGGER.info("Min LocalTime:" + LocalTime.MIN);
        LOGGER.info("Max LocalTime:" + LocalTime.MAX);
        LocalTime now = LocalTime.now();
        LOGGER.info("Current time:" + now);
        LOGGER.info("It's before noon:" + now.isBefore(LocalTime.NOON));
        LOGGER.info("Second of day:" + now.toSecondOfDay() + ", nano of day:" + now.toNanoOfDay());
        LocalTime midnight = LocalTime.MIDNIGHT;
        LOGGER.info("Hours after midnight:" + midnight.until(now, ChronoUnit.HOURS));
        LOGGER.info("One hour ahead:" + now.plus(Duration.ofHours(1)));
        LOGGER.info("Two hours ahead:" + now.plusHours(2));
        LocalTime threeHoursAhead = now.plus(3, ChronoUnit.HOURS);
        LOGGER.info("Three hours ahead:" + threeHoursAhead + ", pass midnight:" + (threeHoursAhead.toNanoOfDay() < now.toNanoOfDay()));
        now = now.withNano(0);
        LOGGER.info("After resetting current nanos:hour=" + now.getHour() + ",minute=" + now.get(ChronoField.MINUTE_OF_HOUR)
                + ", second=" + now.getSecond() + ", nano=" + now.getNano());
    }
}
