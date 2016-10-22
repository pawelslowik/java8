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
        LOGGER.info("Fast forward 1 seconds, milli and nano");
        instantNow = instantNow.plus(1, ChronoUnit.SECONDS).plusMillis(1).plusNanos(1);
        LOGGER.info("Instant now:" + instantNow);
        LOGGER.info("Seconds from Epoch:" + instantNow.getEpochSecond());
        LOGGER.info("Fast forward 1 day");
        instantNow = instantNow.plus(1, ChronoUnit.DAYS);
        LOGGER.info("Instant now:" + instantNow);
        try{
            LOGGER.info("Try to fast forward 1 week...");
            instantNow = instantNow.plus(1, ChronoUnit.WEEKS);
        }
        catch(Exception e){
            LOGGER.error("Operation not allowed", e);
        }
        finally {
            LOGGER.info("Instant now:" + instantNow);
        }
    }
}
