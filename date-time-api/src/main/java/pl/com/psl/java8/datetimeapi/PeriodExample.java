package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;

/**
 * Created by psl on 19.10.16.
 */
public class PeriodExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodExample.class);

    public static void main(String[] args) {
        Period periodBetweenEpochAndNow = Period.between(LocalDate.ofEpochDay(0), LocalDate.now());
        log("Period between Epoch and now:", periodBetweenEpochAndNow);

        Period periodBetweenEpochAndTomorrow = periodBetweenEpochAndNow.plus(Period.ofDays(1));
        log("Period between Epoch and tomorrow:", periodBetweenEpochAndTomorrow);
    }

    private static void log(String prefix, Period period){
        LOGGER.info(prefix + period + "="
                + period.getYears() + " years and " +
                +period.getMonths() + " months and "
                + period.getDays() + " days=total of "
                + period.toTotalMonths() + " months");
    }
}
