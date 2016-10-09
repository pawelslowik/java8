package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.*;

/**
 * Created by psl on 09.10.16.
 */
public class InstantExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstantExample.class);

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("Instant min=" + Instant.MIN + " max=" + Instant.MAX);
        Instant instantNow = Instant.now();
        Instant instantEpoch = Instant.ofEpochSecond(0);
        LOGGER.info("Instant now:" + instantNow);
        LOGGER.info("Instant Epoch:" + instantEpoch);
        LOGGER.info("Now is after Epoch:" + instantNow.isAfter(instantEpoch));
        instantNow = instantNow.with(instantEpoch);
        LOGGER.info("Time travelled to Epoch!");
        LOGGER.info("Instant now:" + instantNow);
        LOGGER.info("Instant Epoch:" + instantEpoch);
        LOGGER.info("Fast forward 10 seconds");
        instantNow = instantNow.plus(10, ChronoUnit.SECONDS);
        LOGGER.info("Instant now:" + instantNow);
        LOGGER.info("Seconds from Epoch:" + instantNow.getEpochSecond());
    }
}
