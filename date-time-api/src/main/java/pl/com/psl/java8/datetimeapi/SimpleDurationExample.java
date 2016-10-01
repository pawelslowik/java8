package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by psl on 25.09.16.
 */
public class SimpleDurationExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDurationExample.class);

    public static void main(String[] args) throws InterruptedException {
        Instant instant1 = Instant.now();
        Thread.sleep(10);
        Instant instant2 = Instant.now();
        Duration durationInstant1Instant2 = Duration.between(instant1, instant2);
        LOGGER.info("Duration between instant1 and instant2 in millis:" + durationInstant1Instant2.toMillis());
        Instant instant3 = Instant.now();
        Duration durationInstant2Instant3 = Duration.between(instant2, instant3);
        LOGGER.info("Duration between instant2 and instant3 in millis:" + durationInstant2Instant3.toMillis());
        LOGGER.info("Difference in durations:" + durationInstant1Instant2.minus(durationInstant2Instant3).abs().toMillis());
        LOGGER.info("Sum of durations:" + durationInstant1Instant2.plus(durationInstant2Instant3).toMillis());
    }
}
