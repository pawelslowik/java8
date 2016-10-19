package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/**
 * Created by psl on 19.10.16.
 */
public class ChronoUnitExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChronoUnitExample.class);

    public static void main(String[] args) {
        LocalDate firstWorldWarStart = LocalDate.of(1914, Month.JULY, 28);
        LocalDate firstWorldWarEnd = LocalDate.of(1918, Month.NOVEMBER, 11);
        LOGGER.info("First World War lasted for " + ChronoUnit.DAYS.between(firstWorldWarStart, firstWorldWarEnd) + " days");
        LocalDate secondWorldWarStart = LocalDate.of(1939, Month.SEPTEMBER, 1);
        LocalDate secondWorldWarEnd = LocalDate.of(1945, Month.SEPTEMBER, 2);
        LOGGER.info("Second World War lasted for " +  ChronoUnit.DAYS.between(secondWorldWarStart, secondWorldWarEnd) + " days");
        LOGGER.info("Second World War started " + ChronoUnit.YEARS.between(secondWorldWarStart, LocalDate.now()) + " years ago");
    }
}
