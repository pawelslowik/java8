package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by psl on 27.09.16.
 */
public class TemporalAdjusterDemo {

    private static final Logger LOG = LoggerFactory.getLogger(LocalDateDemo.class);

    public static void main(String[] args) {

        LocalDate today = LocalDate.now();
        LOG.info("Today is:" + today.getDayOfWeek() + ", " + today);
        LOG.info("Next Monday:" + today.with(TemporalAdjusters.next(DayOfWeek.MONDAY)));
        LOG.info("Previous Monday:" + today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)));
        LOG.info("1st Monday in current month:" + today.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.MONDAY)));
        LOG.info("Last Monday in current month:" + today.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY)));
        LOG.info("Last day in current month:" + today.with(TemporalAdjusters.lastDayOfMonth()));
    }
}
