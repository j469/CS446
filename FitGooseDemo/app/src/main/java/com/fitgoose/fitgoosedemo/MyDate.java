package com.fitgoose.fitgoosedemo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by YuFan on 7/06/15.
 */
public class MyDate {

    private int year, month, day;

    private final int YEAR_OFFSET = 10000;
    private final int MONTH_OFFSET = 100;

    public static final int YEAR = Calendar.YEAR;
    public static final int MONTH = Calendar.MONTH;
    public static final int DATE = Calendar.DATE;

    public MyDate() {

    }

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public MyDate(int timeStamp) {
        this.setTimeStamp(timeStamp);
    }

    public void setTimeStamp(int timeStamp) {
        year = timeStamp / YEAR_OFFSET;
        month = (timeStamp % YEAR_OFFSET) / MONTH_OFFSET;
        day = timeStamp % MONTH_OFFSET;
    }

    public int getTimeStamp() {
        int timeStamp = year * YEAR_OFFSET + month * MONTH_OFFSET + day;
        return timeStamp;
    }

    public void add(int field, int value) {
        Calendar cal = toCalendar();
        cal.add(field, value);
        setFromCalendar(cal);
    }

    public String format(SimpleDateFormat formatter) {
        Calendar cal = toCalendar();
        String formattedString = formatter.format(cal.getTime());
        return formattedString;
    }

    public MyDate clone() {
        return new MyDate(year, month, day);
    }

    public static MyDate getToday() {
        Calendar current = Calendar.getInstance();
        MyDate today = new MyDate();
        today.setFromCalendar(current);
        return today;
    }

    /*
    Note: The following two methods are created because in Calendar January = 0, February = 1
        and so on. So 1 must be offset when converting between MyDate and Calendar.
     */
    private Calendar toCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal;
    }

    private void setFromCalendar(Calendar cal) {
        year = cal.get(YEAR);
        month = cal.get(MONTH) + 1;
        day = cal.get(DATE);
    }


    // Generic Getters and Setters

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
