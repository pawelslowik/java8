package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.*;

/**
 * Created by psl on 09.10.16.
 */
public class InstantExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstantExample.class);

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("Instant min=" + Instant.MIN + " , Epoch seconds:" + Instant.MIN.getEpochSecond());
        LOGGER.info("Instant max=" + Instant.MAX + " , Epoch seconds:" + Instant.MAX.getEpochSecond());
        Instant instantNow = Instant.now();
        logInstantNow(instantNow);
        Instant instantEpoch = Instant.ofEpochSecond(0);
        LOGGER.info("Instant Epoch:" + instantEpoch);
        LOGGER.info("Now is after Epoch:" + instantNow.isAfter(instantEpoch));
        LOGGER.info("Days from Epoch:" + instantEpoch.until(instantNow, ChronoUnit.DAYS));
        instantNow = instantNow.with(instantEpoch);
        LOGGER.info("Time travelled to Epoch!");
        logInstantNow(instantNow);
        LOGGER.info("Instant Epoch:" + instantEpoch);
        LOGGER.info("Fast forward 1 seconds, milli and nano");
        instantNow = instantNow.plus(1, ChronoUnit.SECONDS).plusMillis(1).plusNanos(1);
        logInstantNow(instantNow);
        LOGGER.info("Fast forward 1 day");
        instantNow = instantNow.plus(1, ChronoUnit.DAYS);
        logInstantNow(instantNow);
        LOGGER.info("Days from Epoch:" + instantEpoch.until(instantNow, ChronoUnit.DAYS));
        try {
            LOGGER.info("Try to fast forward 1 week...");
            instantNow = instantNow.plus(1, ChronoUnit.WEEKS);
        } catch (Exception e) {
            LOGGER.error("Operation not allowed", e);
        } finally {
            logInstantNow(instantNow);
        }
    }

    private static void logInstantNow(Instant instantNow) {
        LOGGER.info("Instant now:" + instantNow + " ,Epoch seconds:" + instantNow.getEpochSecond()
                + " , nanoseconds:" + instantNow.getNano());
    }
}
