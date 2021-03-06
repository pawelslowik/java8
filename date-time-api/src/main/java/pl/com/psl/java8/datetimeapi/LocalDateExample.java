package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoUnit;

/**
 * Created by psl on 27.09.16.
 */
public class LocalDateExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateExample.class);

    public static void main(String[] args) {
        LOGGER.info("Min LocalDate:" + LocalDate.MIN + ", max LocalDate:" + LocalDate.MAX);
        LocalDate today = LocalDate.now();
        logDate("Today is:", today);
        LocalDate tomorrow = today.plusDays(1);
        logDate("Tomorrow will be:", tomorrow);

        LocalDate firsDayOfEpoch = LocalDate.ofEpochDay(0);
        logDate("First day of Epoch:", firsDayOfEpoch);
        LocalDate firstAnniversaryOfEpoch = firsDayOfEpoch.withYear(1971);
        logDate("First anniversary of Epoch:", firstAnniversaryOfEpoch);
        Period periodOfEpoch = firsDayOfEpoch.until(today);
        LOGGER.info("Years of Epoch:" + periodOfEpoch.getYears());

        LocalDate christmasDate = LocalDate.of(Year.now().getValue(), Month.DECEMBER, 24);
        LOGGER.info("Christmas is after tomorrow:" + christmasDate.isAfter(tomorrow));
        LOGGER.info("Days until Christmas:" + today.until(christmasDate, ChronoUnit.DAYS));
    }

    private static void logDate(String prefix, LocalDate date){
        LOGGER.info(prefix + date.getDayOfWeek() + ", " + date);
    }


}
