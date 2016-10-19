package pl.com.psl.java8.datetimeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;

/**
 * Created by psl on 19.10.16.
 */
public class PeriodExmple {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodExmple.class);

    public static void main(String[] args) {
        Period periodBetweenEpochAndNow = Period.between(LocalDate.ofEpochDay(0), LocalDate.now());
        LOGGER.info("Period between Epoch and now:" + periodBetweenEpochAndNow + "="
                + periodBetweenEpochAndNow.getYears() + " years and " +
                +periodBetweenEpochAndNow.getMonths() + " months and "
                + periodBetweenEpochAndNow.getDays() + " days=total of "
                + periodBetweenEpochAndNow.toTotalMonths() + " months");

        Period periodBetweenEpochAndTomorrow = periodBetweenEpochAndNow.plus(Period.ofDays(1));
        LOGGER.info("Period between Epoch and tomorrow:" + periodBetweenEpochAndTomorrow + "="
                + periodBetweenEpochAndTomorrow.getYears() + " years and " +
                +periodBetweenEpochAndTomorrow.getMonths() + " months and "
                + periodBetweenEpochAndTomorrow.getDays() + " days=total of "
                + periodBetweenEpochAndTomorrow.toTotalMonths() + " months");

    }
}
