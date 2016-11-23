package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by psl on 23.11.16.
 */
public class ZonedDateTimeExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZonedDateTimeExample.class);

    public static void main(String[] args) {
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        LOGGER.info("LocalDateTime now:" + localDateTimeNow);
        ZonedDateTime zonedDateTimeNowInSystemDefaultZone = ZonedDateTime.of(localDateTimeNow, ZoneId.systemDefault());
        LOGGER.info("LocalDateTime->ZonedDateTime at default system zone id:" + zonedDateTimeNowInSystemDefaultZone);
        LOGGER.info("Default system zone id=" + zonedDateTimeNowInSystemDefaultZone.getZone() + ", offset=" + zonedDateTimeNowInSystemDefaultZone.getOffset());
        LOGGER.info("LocalDateTime->not adjusted ZonedDateTime at America/Chicago zone id:" + localDateTimeNow.atZone(ZoneId.of("America/Chicago")));
        LOGGER.info("ZonedDateTime now at America/Chicago zone id:" + ZonedDateTime.now(ZoneId.of("America/Chicago")));
        Instant instantNow = Instant.now();
        LOGGER.info("Instant now:" + instantNow);
        ZonedDateTime zonedDateTimeChicago = ZonedDateTime.ofInstant(instantNow, ZoneId.of("America/Chicago"));
        LOGGER.info("Instant->ZonedDateTime at America/Chicago zone id:" + zonedDateTimeChicago);
        LOGGER.info("ZonedDateTime at America/Chicago zone id->adjusted ZonedDateTime at GMT zone id:" + zonedDateTimeChicago.withZoneSameInstant(ZoneId.of("GMT")));
        LOGGER.info("ZonedDateTime at America/Chicago zone id->not adjusted ZonedDateTime at GMT zone id:" + zonedDateTimeChicago.withZoneSameLocal(ZoneId.of("GMT")));

        LOGGER.info("All available zone ids:");
        ZoneId.getAvailableZoneIds().forEach(LOGGER::info);
    }
}
