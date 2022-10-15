/*
 * @author vishalbutolia
 * @Project reviewService
 * @Date 16/10/22
 * */

package com.signify.reviewservice.util;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    private DateTimeUtil() {
        throw new IllegalArgumentException("Utility class");
    }
    public static Date incrementADateByOneDay(Date startDate) {
        return Date.from(startDate.toInstant().plus(1, ChronoUnit.DAYS));
    }

    public static Date resetADayTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
