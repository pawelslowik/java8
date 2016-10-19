package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by psl on 17.10.16.
 */
public class DurationExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(DurationExample.class);

    public static void main(String[] args) throws InterruptedException {
        Instant start = Instant.now();
        LOGGER.info("Running long operation...");
        Thread.sleep(5);
        Instant operation1End = Instant.now();
        LOGGER.info("Running another long operation...");
        Thread.sleep(5);
        Instant operation2End = Instant.now();
        Duration operation1Duration = Duration.between(start, operation1End);
        Duration duration2Duration = Duration.between(operation1End, operation2End);
        LOGGER.info("First operation took:" + operation1Duration.toMillis() + " millis");
        LOGGER.info("Second operation took:" + duration2Duration.toMillis() + " millis");
        LOGGER.info("Total operation time:" + operation1Duration.plus(duration2Duration).toMillis() + " millis");

        Duration durationBetweenEpochAndNow = Duration.between(Instant.EPOCH, Instant.now());
        LOGGER.info("Duration between Epoch and now:" + durationBetweenEpochAndNow + "="
                + durationBetweenEpochAndNow.getSeconds() + " seconds and "
                + durationBetweenEpochAndNow.getNano() + " nanos=total of "
                + durationBetweenEpochAndNow.toDays() + " days");

        Duration durationBetweenEpochAndTomorrow = durationBetweenEpochAndNow.plus(Duration.ofDays(1));
        LOGGER.info("Duration between Epoch and tomorrow:" + durationBetweenEpochAndTomorrow + "="
                + durationBetweenEpochAndTomorrow.getSeconds() + " seconds and "
                + durationBetweenEpochAndTomorrow.getNano() + " nanos=total of "
                + durationBetweenEpochAndTomorrow.toDays() + " days");
    }
}
