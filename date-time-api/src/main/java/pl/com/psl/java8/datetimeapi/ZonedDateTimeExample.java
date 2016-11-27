package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;

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

        LocalDate dateBeforeOctoberDaylightSaving = LocalDate.of(2016, Month.OCTOBER, 30);
        ZonedDateTime zonedDateTimeBeforeOctoberDaylightSaving = ZonedDateTime.of(dateBeforeOctoberDaylightSaving, LocalTime.of(2, 59, 59), ZoneId.systemDefault());
        LOGGER.info("ZonedDateTime before October daylight saving:" + zonedDateTimeBeforeOctoberDaylightSaving);
        LOGGER.info("ZonedDateTime after October daylight saving:" + zonedDateTimeBeforeOctoberDaylightSaving.plusSeconds(1));
        LOGGER.info("ZonedDateTime at October overlap with with later offset:" + zonedDateTimeBeforeOctoberDaylightSaving.withLaterOffsetAtOverlap());

        LocalDate dateBeforeMarchDaylightSaving = LocalDate.of(2017, Month.MARCH, 26);
        ZonedDateTime zonedDateTimeBeforeMarchDaylightSaving = ZonedDateTime.of(dateBeforeMarchDaylightSaving, LocalTime.of(1, 59, 59), ZoneId.systemDefault());
        LOGGER.info("ZonedDateTime before March daylight saving:" + zonedDateTimeBeforeMarchDaylightSaving);
        LOGGER.info("ZonedDateTime after March daylight saving:" + zonedDateTimeBeforeMarchDaylightSaving.plusSeconds(1));
        LOGGER.info("ZonedDateTime after March daylight saving with fixed offset zone:" + zonedDateTimeBeforeMarchDaylightSaving.withFixedOffsetZone().plusSeconds(1));

        LOGGER.info("All available European zone ids:");
        ZoneId.getAvailableZoneIds().stream().filter(e -> e.contains("Europe")).sorted().forEach(LOGGER::info);
    }
}
