package com.fitgoose.fitgoosedemo;

import java.util.Calendar;

/**
 * Created by YuFan on 7/06/15.
 */
public class CalendarHelper {

    /**
     * Get the current time with the hour, minute, second and millisecond set to 0.
     * @return The current time with date as the highest precision
     */
    public static Calendar getCurrentDate() {
        Calendar curTime = Calendar.getInstance();
        curTime.set(Calendar.HOUR, 0);
        curTime.set(Calendar.MINUTE, 0);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);

        return curTime;
    }

    /**
     * Strip the hour, minute, second and millisecond precision off of a Calendar object. (Set the
     * aforementioned fields to 0)
     * @param calendar The Calendar item to be converted
     */
    public static void roundToDate(Calendar calendar) {
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
