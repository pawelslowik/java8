package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.temporal.ChronoField;

/**
 * Created by psl on 14.11.16.
 */
public class LocalDateTimeExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateTimeExample.class);

    public static void main(String[] args) {
        LOGGER.info("LocalDateTime min/max=" + LocalDateTime.MIN + "/" + LocalDateTime.MAX);
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        LOGGER.info("LocalDateTime now:" + localDateTimeNow);
        LOGGER.info("LocalDateTime->LocalTime:" + localDateTimeNow.toLocalTime());
        LOGGER.info("LocalDateTime nanosecond of second, like in LocalTime:" + localDateTimeNow.get(ChronoField.NANO_OF_SECOND));
        LOGGER.info("LocalDateTime->LocalDate:" + localDateTimeNow.toLocalDate());
        LOGGER.info("LocalDateTime day of month, like in LocalDate:" + localDateTimeNow.get(ChronoField.DAY_OF_MONTH));
        try{
            LOGGER.info("Trying to get nano of day in int value...");
            LOGGER.info("Nano of day:" + localDateTimeNow.get(ChronoField.NANO_OF_DAY));
        }
        catch(Exception e){
            LOGGER.error(e.toString());
            LOGGER.info("Nano of day in long value:" + localDateTimeNow.getLong(ChronoField.NANO_OF_DAY));
        }

        LOGGER.info("LocalDateTime now at GMT zone id:" + LocalDateTime.now(ZoneId.of("GMT")));
        LOGGER.info("LocalDateTime now at default system zone id:" + LocalDateTime.now(ZoneId.systemDefault()));
        LOGGER.info("LocalDateTime now at America/Chicago zone id:" + LocalDateTime.now(ZoneId.of("America/Chicago")));
    }
}
