/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 16/10/22
 * */

package com.signify.reviewservice.util;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    private DateTimeUtil() {
        throw new IllegalArgumentException("Utility class");
    }

    public static LocalDate incrementADateByOneDay(LocalDate date) {
        return date.plusDays(1);
    }

    public static Date resetADayTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static int getLastDayOfAMonth(LocalDate date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonthValue());
        return calendar.getActualMaximum(Calendar.DATE);
    }

}
