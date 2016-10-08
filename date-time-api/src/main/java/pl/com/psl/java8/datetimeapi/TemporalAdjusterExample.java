package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by psl on 27.09.16.
 */
public class TemporalAdjusterExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemporalAdjusterExample.class);

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LOGGER.info("Today is:" + today.getDayOfWeek() + ", " + today);
        LOGGER.info("Next Monday:" + today.with(TemporalAdjusters.next(DayOfWeek.MONDAY)));
        LOGGER.info("Previous Monday:" + today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)));
        LOGGER.info("1st Monday in current month:" + today.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.MONDAY)));
        LOGGER.info("Last Monday in current month:" + today.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY)));
        LOGGER.info("Last day in current month:" + today.with(TemporalAdjusters.lastDayOfMonth()));
    }
}
